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

    private List<Question> passedQuestions;

    // Последний заддонный вопрос
    private List<Answer> answer = new ArrayList<>();

    public AnswerHelper(User user, int sysCurrentDay, Update update, AnswerDao answerDao, QuestionDao questionDao) {
        this.user = user;
        this.sysCurrentDay = sysCurrentDay;
        this.answerDao = answerDao;
        this.questionDao = questionDao;
        passedQuestions = answerDao.findQuestionAllByUserAndDayAnswer(user, this.sysCurrentDay);

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
        return this.answer.stream().anyMatch(a -> a.getUserAnswer() != null);
    }

    public UserResult getUserResultByCurrentDay() {
        UserResult currentResult = new UserResult();
        answerDao.findAllByUserAndDayAnswer(this.user, this.sysCurrentDay)
            .filter(entity -> entity.getQuestion() != null)
            .forEach(entity ->
                currentResult.incAnswer(entity.getQuestion().getWeight(), entity.getQuestion().checkResult(entity.getUserAnswer())));
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
            this.questionDao.findById(this.questionId)
                .ifPresent(q -> this.answer = answerDao.findAllByUserAndQuestionAndDayAnswer(this.user, q,this.sysCurrentDay));

        } catch (Exception e) {
            LOGGER.warn("Несмогли распарсить ответ: {}", callbackQuery.getData(), e);
        }
    }

    public List<Question> getPassedQuestions() {
        return passedQuestions;
    }

    public List<Answer> getAnswer() {
        return answer;
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

}
