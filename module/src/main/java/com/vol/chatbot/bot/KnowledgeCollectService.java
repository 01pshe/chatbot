package com.vol.chatbot.bot;

import com.vol.chatbot.knowledge.InlineKeyboard;
import com.vol.chatbot.knowledge.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KnowledgeCollectService implements BotService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeCollectService.class);
    private Map<User, List<Task>> knowledge = new ConcurrentHashMap<>();


    @Override
    public SendMessage getMessage(Update update) {
        User user = getUser(update);
        Long chatId = getChadId(update);
        Integer messageId = getMessageId(update);
        LOGGER.info("messageId: {}, chatId: {}, user:{}", messageId, chatId, user);

        if (update.hasCallbackQuery()) {
            addAnswer(update.getCallbackQuery());
        }
        Task task = new Task();
        SendMessage sendMessage = InlineKeyboard.getKeyboard(task);
        sendMessage.setText(task.getQuestion());
        sendMessage.setChatId(chatId);

        List<Task> taskList = knowledge.getOrDefault(user, new ArrayList<>());
        taskList.add(task);
        knowledge.put(user, taskList);
        return sendMessage;

    }

    public List<Task> getUserTask(User user) {
        return knowledge.getOrDefault(user, new ArrayList<>());
    }

    private void addAnswer(CallbackQuery callbackQuery) {
        User user = callbackQuery.getFrom();
        String[] array = callbackQuery.getData().split(";");
        String uuid = array[0];
        String result = array[1];
        knowledge.getOrDefault(user, new ArrayList<>()).stream()
            .filter(task -> task.getUuid().equals(uuid))
            .findAny()
            .ifPresent(task -> task.setAnswer(result));
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

    private User getUser(Update update) {
        User user;
        if (update.hasCallbackQuery()) {
            user = update.getCallbackQuery().getFrom();
        } else {
            user = update.getMessage().getFrom();
        }
        return user;
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
