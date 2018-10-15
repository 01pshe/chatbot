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
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;


@Component
public class Bot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);

    private QueueService queueService;
    private String botToken;
    private BotService answerCollectService;
    private BotService commandProcessor;
    private PropertiesService propertiesService;
    private UserService userService;

    @Autowired
    public Bot(@Qualifier("answerCollectService") BotService answerCollectService,
               @Qualifier("commandProcessor") BotService commandProcessor,
               QueueService queueService,
               PropertiesService propertiesService,
               UserService userService) {
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

    private boolean isCommand(Update update){
        String msgText = Optional.ofNullable(update.getMessage()).map(Message::getText).orElse("");
        return msgText.length() > 0 && msgText.startsWith("/");
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage ;
        if (!propertiesService.getAsBoolean(Properties.SUSPEND_MODE)) {
            User user = userService.getUser(update, getChatId(update));
            if (isCommand(update)) {
                sendMessage = commandProcessor.createResponse(user,update);
            } else {
                sendMessage = answerCollectService.createResponse(user, update);
            }
        } else {
            sendMessage = new SendMessage();
            sendMessage.setText(propertiesService.getAsString(Properties.SUSPEND_TEXT));
        }

        if (sendMessage != null) {
            Long chatId = getChatId(update);
            sendMessage.setChatId(chatId);
            queueService.add(sendMessage);
        } else {
            LOGGER.info("Пустоее сообение не отпровляем!!!");
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


    private Long getChatId(Update update) {
        Long chadId;
        if (update.hasCallbackQuery()) {
            chadId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            chadId = update.getMessage().getChatId();
        }
        return chadId;
    }

}
