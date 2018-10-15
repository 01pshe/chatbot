package com.vol.chatbot.services;

import com.vol.chatbot.model.Answer;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.EntityManager;
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
    private EntityManager entityManager;

    // Вопросы которые уже задовали
    private List<Question> passedQuestions;

    // Вопросы которые ждут ответа
    private List<Answer> expectedAnswers = new ArrayList<>();

    // пришел ожидаемый вопрос
    private boolean isExpectedAnswer = false;

    public AnswerHelper(User user, int sysCurrentDay, Update update, EntityManager entityManager) {
        this.user = user;
        this.sysCurrentDay = sysCurrentDay;
        this.entityManager = entityManager;
        passedQuestions = this.entityManager.createNativeQuery("select q.* from bot_question q, bot_answer a where a.question_id = q.id and a.user_id = :userId and a.day_answer = :dayAnswer", Question.class)
            .setParameter("userId", user.getId())
            .setParameter("dayAnswer", this.sysCurrentDay)
            .getResultList();

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

         List<Answer> answerList = entityManager.createNativeQuery("select * from bot_answer a where a.user_id = :userId and a.day_answer = :dayAnswer and a.question_id is not null", Answer.class)
            .setParameter("userId", this.user.getId())
            .setParameter("dayAnswer", this.sysCurrentDay)
            .getResultList();

        answerList.forEach(
            answer ->
                currentResult.incAnswer(answer.getQuestion().getWeight(), answer.getQuestion().checkResult(answer.getUserAnswer()))
        );

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

            this.expectedAnswers = entityManager.createNativeQuery("select * from bot_answer a where a.user_id = :userId and a.day_answer = :dayAnswer and a.user_answer is null", Answer.class)
                .setParameter("userId", user.getId())
                .setParameter("dayAnswer", this.sysCurrentDay)
                .getResultList();

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

    public String getCurrentResult() {
        Question question = entityManager.find(Question.class,this.questionId);
        return String.valueOf(question.checkResult(this.userAnswer));
    }

}
