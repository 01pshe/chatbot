package com.vol.chatbot.messagesender;

import java.util.Set;

public interface MessageSender {

    boolean sendAll(String messageText);

    boolean sendAllById(String messageText, Set<Long> userIds);

    boolean sendAllByNic(String messageText, Set<String> userNic);
}
