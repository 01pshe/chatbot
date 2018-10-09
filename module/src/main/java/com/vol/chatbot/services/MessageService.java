package com.vol.chatbot.services;

import com.vol.chatbot.dao.MessageDao;
import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class MessageService {

    private MessageDao messageDao;

    @Autowired
    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void save(Message message) {
        messageDao.save(message);
    }

    public Long getCountByUser(User user) {
        return messageDao.countByUser(user);
    }

    public Set<Message> getMessagesByUser(User user) {
        return messageDao.getByUser(user);
    }

    public Message addInboundMessage(User user, org.telegram.telegrambots.meta.api.objects.Message newMessage) {
        Message message = new Message();
        message.setMessageText("U: " + newMessage.getText());
        message.setUser(user);
        message.setDate(newMessage.getDate().longValue());
        message.setInbound(Boolean.TRUE);
        messageDao.save(message);
        return message;
    }

    public Message addOutboundMessage(User user, String messageText) {
        Message message = new Message();
        message.setMessageText("B: " + messageText);
        message.setUser(user);
        message.setDate(new Date().getTime() % 1000);
        message.setInbound(Boolean.FALSE);
        messageDao.save(message);
        return message;
    }


}
