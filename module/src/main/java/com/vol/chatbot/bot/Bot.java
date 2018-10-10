package com.vol.chatbot.bot;

import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.User;
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
    private BotService knowledgeService;
    private PropertiesService propertiesService;
    private UserService userService;

    @Autowired
    public Bot(@Qualifier("knowledgeCollectService") BotService knowledgeService,
               QueueService queueService,
               PropertiesService propertiesService,
               UserService userService) {
        this.knowledgeService = knowledgeService;
        this.queueService = queueService;
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
        SendMessage sendMessage;
        if (!propertiesService.getAsBoolean(Properties.SUSPEND_MODE)) {
            sendMessage = getMessage(update);
        } else {
            sendMessage = new SendMessage();
            sendMessage.setText("Извините. Работа бота приостановлена.");
        }
        Long chatId = getChadId(update);
        sendMessage.setChatId(chatId);
        queueService.add(sendMessage);
    }

    private SendMessage getMessage(Update update) {
        User user = userService.getUser(update);
        return knowledgeService.createResponse(user, update);

    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
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
