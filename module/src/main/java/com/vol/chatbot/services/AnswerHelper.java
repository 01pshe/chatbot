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
import java.util.Optional;

public class AnswerHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerHelper.class);
    private Long questionId;
    private String userAnswer;
    private CallbackQuery callbackQuery;
    private Boolean isCallback;
    private User user;

    private AnswerDao answerDao;
    private QuestionDao questionDao;

    private Optional<Question> question = Optional.empty();

    private List<Question> passedQuestions;
    private List<Answer> answer = new ArrayList<>();


    public AnswerHelper(User user, Update update, AnswerDao answerDao, QuestionDao questionDao) {
        this.user = user;
        this.answerDao = answerDao;
        this.questionDao = questionDao;
        passedQuestions = answerDao.findQuestionAllByUser(user);

        if (update.hasCallbackQuery()) {
            initCallback(update);
        } else {
            initMessage(update);
        }
    }

    public Boolean isEndTest() {
        return QuestionService.DAY_QUESTION_CNT <= passedQuestions.size();
    }

    public Boolean isTwiceAnswered() {
        return this.answer.stream().anyMatch(a -> a.getUserAnswer() != null);
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
            this.question = questionDao.findById(this.questionId);
            this.question.ifPresent(q -> this.answer = answerDao.findAllByUserAndQuestion(this.user, q));

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

    public Long getQuestionId() {
        return questionId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public CallbackQuery getCallbackQuery() {
        return callbackQuery;
    }

    public User getUser() {
        return user;
    }

    public Boolean isCallback() {
        return isCallback;
    }

    public Optional<Question> getQuestion() {
        return question;
    }
}
