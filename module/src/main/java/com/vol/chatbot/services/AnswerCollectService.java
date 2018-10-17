package com.vol.chatbot.services;

import com.vol.chatbot.bot.BotService;
import com.vol.chatbot.keyboard.InlineKeyboard;
import com.vol.chatbot.model.Answer;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.User;
import com.vol.chatbot.services.propertiesservice.PropertiesService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;

@Component
public class AnswerCollectService implements BotService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerCollectService.class);

    private QuestionService questionService;
    private UserService userService;
    private PropertiesService propertiesService;
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public AnswerCollectService(QuestionService questionService,
                                UserService userService,
                                PropertiesService propertiesService,
                                EntityManagerFactory entityManagerFactory) {
        this.questionService = questionService;
        this.userService = userService;
        this.propertiesService = propertiesService;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public SendMessage createResponse(User user, Update update) {

        try (AnswerHelper answerHelper = new AnswerHelper(user, propertiesService.getAsInteger(Properties.CURRENT_DAY), update, this.entityManagerFactory)) {
            SendMessage sendMessage = null;

            // попытка ответить на другой вопрос
            if (answerHelper.isCallback() && !answerHelper.isExpectedAnswer()) {
                return sendMessage;
            }

            // попытка ответить второй раз на вопрос
            if (answerHelper.isCallback() && answerHelper.isTwiceAnswered()) {
                return sendMessage;
            }

            // пользователь завершил текущий день
            if (answerHelper.isCallback() && answerHelper.isEndCurrentDay()) {
                return sendMessage;
            }

            // запишим ответ на вопрос если это отве
            if (answerHelper.isCallback()) {
                saveAnswerByUser(answerHelper);
            }

            // польльзователь ответил на все вопросов, отправим сообщение что спсибо за участие и завершим день
            if (answerHelper.isEndCurrentDayTest()) {
                UserResult currentResult = answerHelper.getUserResultByCurrentDay();

                userService.updateUserResultByCurrentDay(user, currentResult.getPercentage());

                sendMessage = new SendMessage();
                sendMessage.setText(currentResult.getResultMessageByCurrentDay(propertiesService));

                return sendMessage;
            }

            Question questionNew = null;

            // если вопрос не задовали, то задодим
            if ((!answerHelper.isCallback() && answerHelper.getPassedQuestions().isEmpty()) || answerHelper.isCallback()) {
                questionNew = questionService.getQuestion(answerHelper);
            }

            // вопрос получили, сохраним его
            if (questionNew != null) {
                sendMessage = InlineKeyboard.getKeyboard(questionNew);
                sendMessage.setText(questionNew.getQuestion());
                saveQuestionByUser(user, questionNew);
            }

            return sendMessage;

        }

    }

    private void saveAnswerByUser(AnswerHelper helper) {
        helper.getExpectedAnswers().stream()
            .filter(answer -> answer.getUserAnswer() == null && answer.getQuestion() != null &&
                answer.getQuestion().getId().equals(helper.getQuestionId()))
            .forEach(answer -> {
                EntityManager entityManager = this.entityManagerFactory.createEntityManager();
                EntityTransaction transaction = entityManager.getTransaction();

                try {
                    transaction.begin();
                    Answer answerForUpdate = entityManager.find(Answer.class, answer.getId(), LockModeType.PESSIMISTIC_WRITE);
                    if (answerForUpdate.getUserAnswer() == null) {
                        answerForUpdate.setUserAnswer(helper.getUserAnswer());
                        answerForUpdate.setResult(helper.getCurrentResult());
                        answerForUpdate.setDateUserAnswer(new Date());
                        entityManager.merge(answerForUpdate);
                        entityManager.flush();
                        transaction.commit();
                    } else {
                        throw new HibernateException("Запись уже обновлена ранее, имя текущего потока: " + Thread.currentThread().getName());
                    }
                } catch (Exception e) {
                    transaction.rollback();
                    throw e;
                } finally {
                    entityManager.close();
                }

            });
    }

    private void saveQuestionByUser(User user, Question question) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            List list = entityManager.createNativeQuery("select a.* from bot_answer a where a.user_id = :userId and a.day_answer = :dayAnswer and a.question_id = :questionId", Answer.class)
                .setParameter("userId", user.getId())
                .setParameter("dayAnswer", propertiesService.getAsInteger(Properties.CURRENT_DAY))
                .setParameter("questionId", question.getId())
                .getResultList();

            if (list.isEmpty()) {
                Answer answer = new Answer();
                answer.setUser(user);
                answer.setQuestion(question);
                answer.setDayAnswer(propertiesService.getAsInteger(Properties.CURRENT_DAY));
                answer.setDateCreate(new Date());
                answer.setWeight(question.getWeight());
                answer.setUserName(user.getUserFirstName());
                entityManager.persist(answer);
                entityManager.flush();
                transaction.commit();
                LOGGER.info("Пользователю {}, задали вопрос {}", user, question);
            } else {
                throw new HibernateException("Запись уже добавлена ранее, имя текущего потока: " + Thread.currentThread().getName());
            }
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }

    }

}
