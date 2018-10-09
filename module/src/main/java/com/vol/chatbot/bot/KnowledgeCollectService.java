package com.vol.chatbot.bot;

import com.vol.chatbot.dao.AnswerDao;
import com.vol.chatbot.dao.QuestionDao;
import com.vol.chatbot.dao.UserDao;
import com.vol.chatbot.knowledge.InlineKeyboard;
import com.vol.chatbot.knowledge.QuestionFactory;
import com.vol.chatbot.model.Answer;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class KnowledgeCollectService implements BotService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeCollectService.class);

    private QuestionFactory questionFactory;
    private AnswerDao answerDao;
    private QuestionDao questionDao;

    @Autowired
    public KnowledgeCollectService(QuestionFactory questionFactory, UserDao userDao, AnswerDao answerDao, QuestionDao questionDao) {
        this.questionFactory = questionFactory;
        this.answerDao = answerDao;
        this.questionDao = questionDao;
    }

    @Transactional
    @Override
    public SendMessage getMessage(User user, Update update) {

        Integer messageId = getMessageId(update);
        LOGGER.info("messageId: {}, user:{}", messageId, user);

        if (update.hasCallbackQuery()) {
            saveAnswerByUser(user, update.getCallbackQuery());
        }
        Question question = questionFactory.getQuestion(user);

        SendMessage sendMessage = InlineKeyboard.getKeyboard(question);
        sendMessage.setText(question.getQuestion());

        saveQuestionByUser(user, question);

        return sendMessage;

    }

    private void saveAnswerByUser(User user, CallbackQuery callbackQuery) {
        Long questionId;
        String userAnswer;
        try {
            String[] array = callbackQuery.getData().split(";");
            questionId = Long.valueOf(array[0]);
            userAnswer = array[1];
        } catch (Exception e) {
            LOGGER.warn("Несмогли распарсить ответ: {}", callbackQuery.getData(), e);
            return;
        }
        Question question = questionDao.findById(questionId).orElse(null);
        List<Answer> answers = answerDao.findAllByUserAndQuestion(user, question);

        if (answers.size() != 1) {
            LOGGER.warn("Вопрос уже был задан несколько раз");
        }

        for (Answer answer : answers) {
            if (answer.getAnswer() == null) {
                answer.setAnswer(userAnswer);
                answerDao.saveAndFlush(answer);
            }
        }

    }

    private void saveQuestionByUser(User user, Question question) {
        Answer answer = new Answer();
        answer.setUser(user);
        answer.setQuestion(question);
        answerDao.saveAndFlush(answer);
        LOGGER.info("Пользователю {}, задали вопрос {}", user, question);
    }

    private Integer getMessageId(Update update) {
        Integer messageId;
        if (update.hasCallbackQuery()) {
            messageId = update.getCallbackQuery().getMessage().getMessageId();
        } else {
            messageId = update.getMessage().getMessageId();
        }
        return messageId;
    }

}
