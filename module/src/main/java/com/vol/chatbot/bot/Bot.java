package com.vol.chatbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class Bot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);

    private BotService informationService;
    private BotService knowledgeService;

    @Autowired
    public Bot(@Qualifier("informationCollectService") BotService informationService,
               @Qualifier("knowledgeCollectService") BotService knowledgeService) {
        this.informationService = informationService;
        this.knowledgeService = knowledgeService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = getMessage(update);
            execute(sendMessage);
        } catch (Exception e) {
            LOGGER.error("Ошибка отправки сообщения:", e);
        }
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
        return "name_bot";
    }

    @Override
    public String getBotToken() {
        return "token";
    }

}
