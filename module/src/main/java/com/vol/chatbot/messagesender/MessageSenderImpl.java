package com.vol.chatbot.messagesender;

import com.vol.chatbot.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageSenderImpl implements MessageSender {

    private UserDao userDao;

    @Autowired
    public MessageSenderImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public boolean sendAll(String messageText) {

        return false;
    }

    @Override
    public boolean sendAllById(String messageText, Set<Long> userIds) {
        return false;
    }

    @Override
    public boolean sendAllByNic(String messageText, Set<String> userNic) {
        return false;
    }
}
