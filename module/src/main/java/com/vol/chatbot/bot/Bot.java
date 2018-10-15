package com.vol.chatbot.bot;

import com.vol.chatbot.pool.MessageHandler;
import com.vol.chatbot.pool.MessageProcessing;
import com.vol.chatbot.queue.QueueService;
import com.vol.chatbot.services.UserService;
import com.vol.chatbot.services.propertiesservice.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



@Component
public class Bot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);

    private MessageProcessing processing;
    private QueueService queueService;
    private String botToken;
    private BotService answerCollectService;
    private BotService commandProcessor;
    private PropertiesService propertiesService;
    private UserService userService;

    @Autowired
    public Bot(MessageProcessing processing, @Qualifier("answerCollectService") BotService answerCollectService,
               @Qualifier("commandProcessor") BotService commandProcessor,
               QueueService queueService,
               PropertiesService propertiesService,
               UserService userService) {
        this.processing = processing;
        this.answerCollectService = answerCollectService;
        this.queueService = queueService;
        this.commandProcessor = commandProcessor;
        this.propertiesService = propertiesService;
        this.userService = userService;
        this.queueService.setMessageSender(message -> {
            try {
                this.execute(message);
            } catch (TelegramApiException e) {
                LOGGER.warn("ошибка отправки сообщения", e);
                LOGGER.warn("message:{}", message);
            }
        });
        this.queueService.start();
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Thread thisThread = Thread.currentThread();
        ClassLoader currentClassLoader = thisThread.getContextClassLoader();
        MessageHandler messageHandler = new MessageHandler(update, this.queueService,
            this.answerCollectService,
            this.commandProcessor,
            this.propertiesService,
            this.userService,
            currentClassLoader
        );

        processing.execute(messageHandler);

    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

}
