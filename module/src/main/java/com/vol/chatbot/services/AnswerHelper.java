package com.vol.chatbot.services;

import com.vol.chatbot.keyboard.InlineKeyboard;
import com.vol.chatbot.model.Answer;
import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;


public class AnswerHelper implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerHelper.class);
    private Long questionId;
    private String userAnswer;
    private Boolean isCallback;
    private User user;
    private int sysCurrentDay;

    private EntityManager entityManager;

    // Вопросы которые уже задовали
    private List<Question> passedQuestions;

    // Вопросы которые ждут ответа
    private List<Answer> expectedAnswers;

    // пришел ожидаемый вопрос
    private boolean isExpectedAnswer = false;


    public AnswerHelper(User user, int sysCurrentDay, Update update, EntityManagerFactory entityManagerFactory) {
        this.user = user;
        this.sysCurrentDay = sysCurrentDay;
        this.entityManager = entityManagerFactory.createEntityManager();

        passedQuestions = this.entityManager.createNativeQuery("select q.* from bot_question q, bot_answer a where a.question_id = q.id and a.user_id = :userId and a.day_answer = :dayAnswer", Question.class)
            .setParameter("userId", this.user.getId())
            .setParameter("dayAnswer", this.sysCurrentDay)
            .getResultList();

        this.expectedAnswers = this.entityManager.createNativeQuery("select a.* from bot_answer a where a.user_id = :userId and a.day_answer = :dayAnswer and a.user_answer is null", Answer.class)
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

    public Boolean isTwiceAnswered() {
        return this.expectedAnswers.stream().anyMatch(a -> a.getUserAnswer() != null);
    }

    public UserResult getUserResultByCurrentDay() {
        UserResult currentResult = new UserResult(this.user);

        List<Answer> answerList = this.entityManager.createNativeQuery("select a.* from bot_answer a where a.user_id = :userId and a.day_answer = :dayAnswer and a.question_id is not null", Answer.class)
            .setParameter("userId", this.user.getId())
            .setParameter("dayAnswer", this.sysCurrentDay)
            .getResultList();

        answerList.forEach(
            entity -> {
                if (entity.getUserAnswer() == null) {
                    this.entityManager.refresh(entity);
                }
                currentResult.incAnswer(entity.getQuestion().getWeight(), entity.getQuestion().checkResult(entity.getUserAnswer()));
            }
        );


        return currentResult;
    }

    public SendMessage getQuestionLastMessage() {
        SendMessage sendMessage = null;
        if (this.expectedAnswers.size() == 1) {
            Question questionLast = this.expectedAnswers.get(0).getQuestion();
            sendMessage = InlineKeyboard.getKeyboard(questionLast);
            sendMessage.setText(questionLast.getQuestion());
        } else {
            LOGGER.info("Не смогли определить последнее не отвеченное сообщение, expectedAnswers: {}", this.expectedAnswers);
        }
        return sendMessage;

    }


    private void initMessage(Update update) {
        isCallback = Boolean.FALSE;

        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Message message = new Message();
            message.setDate(new Date());
            message.setInbound(Boolean.TRUE);
            message.setMessageText(update.getMessage().getText());
            message.setUser(this.user);
            message.setNameUser(this.user.getUserFirstName());
            this.entityManager.persist(message);
            this.entityManager.flush();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    private void initCallback(Update update) {
        isCallback = Boolean.TRUE;
        try {
            int index = update.getCallbackQuery().getData().indexOf(';');
            this.questionId = Long.valueOf(update.getCallbackQuery().getData().substring(0, index));
            this.userAnswer = update.getCallbackQuery().getData().substring(index + 1);

            if (this.expectedAnswers.size() == 1 && this.expectedAnswers.get(0).getQuestion() != null &&
                this.expectedAnswers.get(0).getQuestion().getId().equals(this.questionId)) {
                this.isExpectedAnswer = true;
            }

        } catch (Exception e) {
            LOGGER.warn("Несмогли распарсить ответ: {}", update.getCallbackQuery().getData(), e);
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
        Question question = this.entityManager.find(Question.class, this.questionId);
        return String.valueOf(question.checkResult(this.userAnswer));
    }

    public boolean startFirst() {

        boolean start = false;
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User userLock = this.entityManager.find(User.class, this.user.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (!userLock.isStartFirst()) {
                userLock.setStartFirst(true);
                entityManager.merge(userLock);
                entityManager.flush();
                start = true;
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            start = false;
        }

        return start;
    }


    @Override
    public void close() {
        if (this.entityManager != null) {
            this.entityManager.close();
        }

    }

    public boolean startSecond() {
        boolean start = false;
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User userLock = this.entityManager.find(User.class, this.user.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (!userLock.isStartSecond()) {
                userLock.setStartSecond(true);
                entityManager.merge(userLock);
                entityManager.flush();
                start = true;
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            start = false;
        }

        return start;
    }
}
