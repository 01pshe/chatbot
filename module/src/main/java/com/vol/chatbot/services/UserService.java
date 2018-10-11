package com.vol.chatbot.services;

import com.vol.chatbot.dao.UserDao;
import com.vol.chatbot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
import java.util.Set;


@Component
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(User user) {
        LOGGER.trace("Saving user id= {}, signature= {}.", user.getId(), user.getSignature());
        userDao.save(user);
        LOGGER.trace("User saved.");
    }

    public User getBySignature(String signature) {
        LOGGER.trace("getting user by signature: {}.", signature);
        User user = userDao.findBySignature(signature);
        if (user != null) {
            LOGGER.trace("got user by signature. id= {}.", user.getId());
        }
        return user;
    }

    public User getById(Long id) {
        LOGGER.trace("getting user by Id: {}.", id);
        User user = userDao.getById(id);
        LOGGER.trace("got user by id. signature= {}.", user.getSignature());
        return user;
    }

    public Set<User> users() {
        LOGGER.trace("getting all users.");
        Set<User> set = userDao.getAllByIdNotNull();
        LOGGER.trace("got all users.");
        return set;
    }

    public User getUser(Update update) {
        org.telegram.telegrambots.meta.api.objects.User telegramUser;
        if (update.hasCallbackQuery()) {
            telegramUser = update.getCallbackQuery().getFrom();
        } else {
            telegramUser = update.getMessage().getFrom();
        }
        return getUser(telegramUser);
    }


    public User getUser(org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        User user = getBySignature(telegramUser.getId().toString());
        if (user != null) {
            return user;
        }
        user = createUser(telegramUser);
        return user;

    }

    public User createUser(org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        User user;
        user = new User();
        user.setBot(telegramUser.getBot());
        user.setSignature(telegramUser.getId().toString());
        user.setUserFirstName(telegramUser.getFirstName());
        user.setUserLastName(telegramUser.getLastName());
        user.setUserName(telegramUser.getUserName());
        user.setDatecreate(new Date());
        user.setPassDay(0);
        save(user);
        return user;
    }
}
