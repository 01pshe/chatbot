package com.vol.chatbot.bot;

import com.vol.chatbot.queue.QueueService;
import com.vol.chatbot.model.Properties;
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

    @Autowired
    public Bot(@Qualifier("informationCollectService") BotService informationService,
               @Qualifier("knowledgeCollectService") BotService knowledgeService,
               QueueService queueService,
               PropertiesService propertiesService) {
        this.informationService = informationService;
        this.knowledgeService = knowledgeService;
        this.queueService = queueService;
        this.propertiesService = propertiesService;
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
        if (!propertiesService.getAsBoolean(Properties.SuspendMode)) {
            sendMessage = getMessage(update);
        } else {
            sendMessage = new SendMessage();
            if (update.getMessage()!=null) {
                sendMessage.setChatId(update.getMessage().getChatId());
            } else if (update.getCallbackQuery()!=null){
                sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
            }
            sendMessage.setText("Извините. Работа бота приостановлена.");
        }
        queueService.add(sendMessage);
    }

    private SendMessage getMessage(Update update) {
        if (update.hasCallbackQuery() || (update.getMessage().getText().equals("/run test"))) {
            return knowledgeService.getMessage(update);
        } else {
            return informationService.getMessage(update);
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
}
