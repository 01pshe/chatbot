package com.vol.chatbot.messagesender;

import java.util.Set;

public interface MessageSender {

    void sendAll(String messageText);

    void sendAllById(String messageText, Set<Long> userIds);

    void sendAllByNic(String messageText, Set<String> userFirstName);
}
