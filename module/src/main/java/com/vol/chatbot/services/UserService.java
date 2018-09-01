package com.vol.chatbot.services;

import com.vol.chatbot.dao.MessageDao;
import com.vol.chatbot.dao.UserDao;
import com.vol.chatbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao, MessageDao messageDao) {
        this.userDao = userDao;
    }

    public void save(User user) {
        userDao.save(user);
    }

    public User getBySignature(String signature) {
        return userDao.findBySignature(signature);
    }

    public User getById(Long id){
        return userDao.findBySysId(id);
    }

    public Set<User> users(){
        return userDao.getAllBySysIdNotNull();
    }
}
