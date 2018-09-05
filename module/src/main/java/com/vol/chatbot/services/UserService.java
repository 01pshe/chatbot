package com.vol.chatbot.services;

import com.vol.chatbot.dao.MessageDao;
import com.vol.chatbot.dao.UserDao;
import com.vol.chatbot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao, MessageDao messageDao) {
        this.userDao = userDao;
    }

    public void save(User user) {
        LOGGER.trace("Saving user id= {}, signature= {}.",user.getId(), user.getSignature());
        userDao.save(user);
        LOGGER.trace("User saved.");
    }

    public User getBySignature(String signature) {
        LOGGER.trace("getting user by signature: {}.",signature);
        User user = userDao.findBySignature(signature);
        LOGGER.trace("got user by signature. id= {}.",user.getId());
        return user;
    }

    public User getById(Long id){
        LOGGER.trace("getting user by Id: {}.",id);
        User user = userDao.getById(id);
        LOGGER.trace("got user by id. signature= {}.",user.getSignature());
        return user;
    }

    public Set<User> users(){
        LOGGER.trace("getting all users.");
        Set<User> set = userDao.getAllByIdNotNull();
        LOGGER.trace("got all users.");
        return set;
    }
}
