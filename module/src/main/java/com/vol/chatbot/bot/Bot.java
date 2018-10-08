package com.vol.chatbot.bot;

import com.vol.chatbot.model.impl.Properties;
import com.vol.chatbot.model.impl.User;
import com.vol.chatbot.queue.QueueService;
import com.vol.chatbot.services.UserService;
import com.vol.chatbot.services.propertiesservice.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class Bot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);

    private QueueService queueService;
    private String botToken;
    private BotService informationService;
    private BotService knowledgeService;
    private PropertiesService propertiesService;
    private UserService userService;

    @Autowired
    public Bot(@Qualifier("informationCollectService") BotService informationService,
               @Qualifier("knowledgeCollectService") BotService knowledgeService,
               QueueService queueService,
               PropertiesService propertiesService, UserService userService) {
        this.informationService = informationService;
        this.knowledgeService = knowledgeService;
        this.queueService = queueService;
        this.propertiesService = propertiesService;
        this.userService = userService;
        this.queueService.setMessageSender(message -> {
            try {
                this.execute(message);
            } catch (TelegramApiException e) {
                LOGGER.warn("ошибка отправки сообщения", e);
            }
        });
        this.queueService.start();
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage;
        Long chatId = getChadId(update);
        if (!propertiesService.getAsBoolean(Properties.SuspendMode)) {
            sendMessage = getMessage(update);
        } else {
            sendMessage = new SendMessage();
            sendMessage.setText("Извините. Работа бота приостановлена.");
        }
        sendMessage.setChatId(chatId);
        queueService.add(sendMessage);
    }

    private SendMessage getMessage(Update update) {
        User user = getUser(update);
        if (update.hasCallbackQuery() || (update.getMessage().getText().equals("/run test"))) {
            return knowledgeService.getMessage(user, update);
        } else {
            return informationService.getMessage(user, update);
        }
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    private User getUser(Update update) {
        org.telegram.telegrambots.meta.api.objects.User user;
        if (update.hasCallbackQuery()) {
            user = update.getCallbackQuery().getFrom();
        } else {
            user = update.getMessage().getFrom();
        }
        return userService.getBySignature(user.getId().toString());
    }

    private Long getChadId(Update update) {
        Long chadId;
        if (update.hasCallbackQuery()) {
            chadId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            chadId = update.getMessage().getChatId();
        }
        return chadId;
    }

}
