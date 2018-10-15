package com.vol.chatbot.messagesender;

import com.vol.chatbot.dao.UserDao;
import com.vol.chatbot.queue.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageSenderImpl implements MessageSender {

    private UserDao userDao;

    private QueueService queueService;

    @Autowired
    public MessageSenderImpl(UserDao userDao,
                             QueueService queueService) {
        this.userDao = userDao;
        this.queueService = queueService;
    }

    private void sendByChatIdIterator(Iterator<Long> chatIdIterator, String messageText){
        while (chatIdIterator.hasNext()){
            SendMessage message = new SendMessage();
            message.enableMarkdown(true);
            message.setText(messageText);
            message.setChatId(chatIdIterator.next());
            queueService.add(message);
        }
    }

    @Override
    public void sendAll(String messageText) {
        Iterator<Long> chatIdIterator = userDao.getAllUsersChatId().iterator();
        sendByChatIdIterator(chatIdIterator,messageText);
    }

    @Override
    public void sendAllById(String messageText, Set<Long> userIds) {
        Iterator<Long> chatIdIterator = userDao.getAllUsersChatIdByIdSet(userIds).iterator();
        sendByChatIdIterator(chatIdIterator,messageText);
    }

    @Override
    public void sendAllByNic(String messageText, Set<String> userFirstName) {
        Set<String> users = userFirstName.stream().map(str->str.toUpperCase()).collect(Collectors.toSet());
        Iterator<Long> chatIdIterator = userDao.getAllUsersChatIdByFirstNameSet(users).iterator();
        sendByChatIdIterator(chatIdIterator,messageText);
    }
}
