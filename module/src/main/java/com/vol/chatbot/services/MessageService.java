package com.vol.chatbot.services;

import com.vol.chatbot.dao.MessageDao;
import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MessageService {

    private MessageDao messageDao;

    @Autowired
    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void save(Message message){
        messageDao.save(message);
    }

    public Long getCountByUser(User user){
        return messageDao.countByUser(user);
    }

    public Set<Message> getMessagesByUser(User user){
        return messageDao.getByUser(user);
    }


}
