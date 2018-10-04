package com.vol.chatbot.queue;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@FunctionalInterface
public interface SendMessageMethod {
    void sendMessage(SendMessage message);
}
