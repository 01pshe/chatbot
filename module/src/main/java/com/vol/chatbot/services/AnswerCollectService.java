package com.vol.chatbot.services;

import com.vol.chatbot.bot.BotService;
import com.vol.chatbot.dao.AnswerDao;
import com.vol.chatbot.dao.QuestionDao;
import com.vol.chatbot.dao.UserDao;
import com.vol.chatbot.keyboard.InlineKeyboard;
import com.vol.chatbot.model.Answer;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AnswerCollectService implements BotService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerCollectService.class);

    private QuestionService questionService;
    private AnswerDao answerDao;
    private QuestionDao questionDao;
    private UserDao userDao;

    @Autowired
    public AnswerCollectService(QuestionService questionService,
                                AnswerDao answerDao,
                                QuestionDao questionDao,
                                UserDao userDao) {
        this.questionService = questionService;
        this.answerDao = answerDao;
        this.questionDao = questionDao;
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public SendMessage createResponse(User user, Update update) {
        AnswerHelper answerHelper = new AnswerHelper(user, update, answerDao, questionDao);

        // польльзователь ответил на 20 вопросов, отправим сообщение что спсибо за участие
        if (answerHelper.isEndTest()) {
            user.setPassDay(user.getPassDay() + 1);
            userDao.saveAndFlush(user);
            return null;
        }

        // попытка ответить второй раз на вопрос
        if (answerHelper.isTwiceAnswered()) {
            return null;
        }

        if (update.hasCallbackQuery()) {
            saveAnswerByUser(answerHelper);
        }

        Question questionNew = questionService.getQuestion(answerHelper);

        SendMessage sendMessage = null;

        if (questionNew != null) {
            sendMessage = InlineKeyboard.getKeyboard(questionNew);
            sendMessage.setText(questionNew.getQuestion());
            saveQuestionByUser(user, questionNew);
        }

        return sendMessage;

    }

    private void saveAnswerByUser(AnswerHelper helper) {
        for (Answer answer : helper.getAnswer()) {
            if (answer.getUserAnswer() == null) {
                answer.setUserAnswer(helper.getUserAnswer());
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


}