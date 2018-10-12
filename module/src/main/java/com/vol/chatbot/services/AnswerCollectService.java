package com.vol.chatbot.services;

import com.vol.chatbot.bot.BotService;
import com.vol.chatbot.dao.AnswerDao;
import com.vol.chatbot.dao.QuestionDao;
import com.vol.chatbot.dao.UserDao;
import com.vol.chatbot.keyboard.InlineKeyboard;
import com.vol.chatbot.model.Answer;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.User;
import com.vol.chatbot.services.propertiesservice.PropertiesService;
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
    private UserService userService;
    private PropertiesService propertiesService;

    @Autowired
    public AnswerCollectService(QuestionService questionService,
                                AnswerDao answerDao,
                                QuestionDao questionDao,
                                UserDao userDao,
                                UserService userService, PropertiesService propertiesService) {
        this.questionService = questionService;
        this.answerDao = answerDao;
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.userService = userService;
        this.propertiesService = propertiesService;
    }

    @Transactional
    @Override
    public SendMessage createResponse(User user, Update update) {

        AnswerHelper answerHelper = new AnswerHelper(user,
            propertiesService.getAsInteger(Properties.CURRENT_DAY),
            update,
            answerDao,
            questionDao
        );

        // попытка ответить второй раз на вопрос
        if (answerHelper.isTwiceAnswered()) {
            return null;
        }

        // пользователь завершил текущий день
        if (answerHelper.isEndCurrentDay()) {
            return null;
        }

        // запишим ответ на вопрос если это отве
        if (update.hasCallbackQuery()) {
            saveAnswerByUser(answerHelper);
        }



        // польльзователь ответил на все вопросов, отправим сообщение что спсибо за участие и завершим день
        if (answerHelper.isEndCurrentDayTest()) {
            UserResult currentResult = answerHelper.getUserResultByCurrentDay();
            userService.updateUserResultByCurrentDay(user, currentResult);
            SendMessage sendMessage = new SendMessage();


            return sendMessage;
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
        answer.setDayAnswer(propertiesService.getAsInteger(Properties.CURRENT_DAY));
        answerDao.saveAndFlush(answer);
        LOGGER.info("Пользователю {}, задали вопрос {}", user, question);
    }

}
