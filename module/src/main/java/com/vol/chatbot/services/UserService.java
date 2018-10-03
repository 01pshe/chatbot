package com.vol.chatbot.services;

import com.vol.chatbot.dao.ScenarioDao;
import com.vol.chatbot.dao.UserDao;
import com.vol.chatbot.model.Scenario;
import com.vol.chatbot.model.User;
import com.vol.chatbot.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;


@Component
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;
    private ScenarioDao scenarioDao;

    @Autowired
    public UserService(UserDao userDao, ScenarioDao scenarioDao) {
        this.userDao = userDao;
        this.scenarioDao = scenarioDao;
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

    public User getUser(String signature, org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        User user = getBySignature(signature);
        if (user != null) {
            return user;
        }
        user = new User();
        user.setBot(telegramUser.getBot());
        user.setSignature(signature);
        user.setUserFirstName(telegramUser.getFirstName());
        user.setUserLastName(telegramUser.getLastName());
        user.setUserName(telegramUser.getUserName());
        user.setDatecreate(new Date());
        Scenario scenario = new Scenario();
        scenario.setUser(user);
        scenario.setCurrentStepNumber(0);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(scenario.getId());
        user.setScenario(scenario);
        user.setUserInfo(userInfo);
        save(user);
        return user;
    }
}
