package com.vol.chatbot.services;

import com.vol.chatbot.dao.AnswerDao;
import com.vol.chatbot.dao.QuestionDao;
import com.vol.chatbot.model.Answer;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerHelper.class);
    private Long questionId;
    private String userAnswer;
    private CallbackQuery callbackQuery;
    private Boolean isCallback;
    private User user;
    private int sysCurrentDay;
    private AnswerDao answerDao;
    private QuestionDao questionDao;

    // Вопросы которые уже задовали
    private List<Question> passedQuestions;

    // Вопросы которые ждут ответа
    private List<Answer> expectedAnswers = new ArrayList<>();

    // пришел ожидаемый вопрос
    private boolean isExpectedAnswer = false;

    public AnswerHelper(User user, int sysCurrentDay, Update update, AnswerDao answerDao, QuestionDao questionDao) {
        this.user = user;
        this.sysCurrentDay = sysCurrentDay;
        this.answerDao = answerDao;
        this.questionDao = questionDao;
        try {
            passedQuestions = this.answerDao.findQuestionAllByUserAndDayAnswer(user, this.sysCurrentDay);
        } catch (Exception e) {
            passedQuestions = this.answerDao.findAll().stream()
                .filter(answer -> answer.getUser().getId().equals(this.user.getId()) && answer.getDayAnswer().equals(this.sysCurrentDay))
                .map(Answer::getQuestion)
                .collect(Collectors.toList());
//            LOGGER.info("passedQuestions Ошибка получения пройденных вопросов: ", e);
        }

        if (update.hasCallbackQuery()) {
            initCallback(update);
        } else {
            initMessage(update);
        }
    }

    public Boolean isEndCurrentDay() {
        return user.getPassDay() >= this.sysCurrentDay;
    }

    public Boolean isEndCurrentDayTest() {
        return Constants.DAY_QUESTION_CNT <= passedQuestions.size();
    }

    public Boolean isTwiceAnswered() {
        return this.expectedAnswers.stream().anyMatch(a -> a.getUserAnswer() != null);
    }

    public UserResult getUserResultByCurrentDay() {
        UserResult currentResult = new UserResult();
        try {
            answerDao.findAllByUserAndDayAnswer(this.user, this.sysCurrentDay)
                .filter(entity -> entity.getQuestion() != null)
                .forEach(entity ->
                    currentResult.incAnswer(entity.getQuestion().getWeight(), entity.getQuestion().checkResult(entity.getUserAnswer())));
        } catch (Exception e) {
            answerDao.findAll().stream()
                .filter(answer -> answer.getUser().getId().equals(this.user.getId()) && answer.getDayAnswer().equals(this.sysCurrentDay) && answer.getQuestion() != null)
                .forEach(answer ->
                    currentResult.incAnswer(answer.getQuestion().getWeight(), answer.getQuestion().checkResult(answer.getUserAnswer())));
//            LOGGER.info("currentResult Ошибка получения результата: ", e);
        }
        return currentResult;
    }

    private void initMessage(Update update) {
        isCallback = Boolean.FALSE;
    }

    private void initCallback(Update update) {
        isCallback = Boolean.TRUE;
        this.callbackQuery = update.getCallbackQuery();
        try {
            String[] array = callbackQuery.getData().split(";");
            this.questionId = Long.valueOf(array[0]);
            this.userAnswer = array[1];
            try {
                this.expectedAnswers = answerDao.findAllByUserAndUserAnswerAndDayAnswer(this.user, null, this.sysCurrentDay);
            } catch (Exception e) {
                this.expectedAnswers = answerDao.findAll().stream()
                    .filter(answer -> answer.getUser().getId().equals(this.user.getId()) && answer.getUserAnswer() == null && answer.getDayAnswer().equals(this.sysCurrentDay))
                    .collect(Collectors.toList());
//                LOGGER.info("passedQuestions Ошибка получения ожидаемых вопросов: ", e);
            }
            if (this.expectedAnswers.size() == 1 && this.expectedAnswers.get(0).getQuestion() != null &&
                this.expectedAnswers.get(0).getQuestion().getId().equals(this.questionId)) {
                this.isExpectedAnswer = true;
            }

        } catch (Exception e) {
            LOGGER.warn("Несмогли распарсить ответ: {}", callbackQuery.getData(), e);
        }
    }

    public List<Question> getPassedQuestions() {
        return passedQuestions;
    }

    public List<Answer> getExpectedAnswers() {
        return expectedAnswers;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public User getUser() {
        return user;
    }

    public int getSysCurrentDay() {
        return sysCurrentDay;
    }

    public boolean isExpectedAnswer() {
        return this.isExpectedAnswer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public Boolean isCallback() {
        return isCallback;
    }
}
