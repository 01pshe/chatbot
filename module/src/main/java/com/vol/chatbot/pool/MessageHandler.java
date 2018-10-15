package com.vol.chatbot.pool;

import com.vol.chatbot.bot.BotService;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.User;
import com.vol.chatbot.queue.QueueService;
import com.vol.chatbot.services.UserService;
import com.vol.chatbot.services.propertiesservice.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.concurrent.RecursiveAction;

public class MessageHandler extends RecursiveAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);
    private QueueService queueService;

    private BotService answerCollectService;
    private BotService commandProcessor;
    private PropertiesService propertiesService;
    private UserService userService;
    private Update update;
    private ClassLoader classLoader;


    public MessageHandler(Update update,
                          QueueService queueService,
                          BotService answerCollectService,
                          BotService commandProcessor,
                          PropertiesService propertiesService,
                          UserService userService,
                          ClassLoader classLoader) {
        this.queueService = queueService;
        this.answerCollectService = answerCollectService;
        this.commandProcessor = commandProcessor;
        this.propertiesService = propertiesService;
        this.userService = userService;
        this.update = update;
        this.classLoader = classLoader;
    }

    @Override
    protected void compute() {
        Thread thisThread = Thread.currentThread();
        ClassLoader oldClassLoader = thisThread.getContextClassLoader();
        thisThread.setContextClassLoader(classLoader);

        try {
            SendMessage sendMessage;
            if (!propertiesService.getAsBoolean(Properties.SUSPEND_MODE)) {
                User user = userService.getUser(update,getChadId(update));
                if (isCommand(update)) {
                    sendMessage = commandProcessor.createResponse(user, update);
                } else {
                    sendMessage = answerCollectService.createResponse(user, update);
                }
            } else {
                sendMessage = new SendMessage();
                sendMessage.setText(propertiesService.getAsString(Properties.SUSPEND_TEXT));
            }

            if (sendMessage != null) {
                Long chatId = getChadId(update);
                sendMessage.setChatId(chatId);
                queueService.add(sendMessage);
            } else {
                LOGGER.info("Пустое сообение не отправляем!!!");
            }
        } catch (Exception e) {
            LOGGER.warn(String.format("Сообщение не отправлено: %s", e.getMessage()));
        }
        thisThread.setContextClassLoader(oldClassLoader);

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


    private boolean isCommand(Update update) {
        String msgText = Optional.ofNullable(update.getMessage()).map(Message::getText).orElse("");
        return msgText.length() > 0 && msgText.startsWith("/");
    }
}
