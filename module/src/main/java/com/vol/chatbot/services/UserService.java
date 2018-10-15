package com.vol.chatbot.services;

import com.vol.chatbot.dao.UserDao;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.User;
import com.vol.chatbot.services.propertiesservice.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Component
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;
    private PropertiesService propertiesService;

    @Autowired
    public UserService(UserDao userDao, PropertiesService propertiesService) {
        this.userDao = userDao;
        this.propertiesService = propertiesService;
    }

    public void save(User user) {
        LOGGER.trace("Saving user id= {}, signature= {}.", user.getId(), user.getSignature());
        userDao.saveAndFlush(user);
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

    public User getUser(Update update, Long chatId) {
        org.telegram.telegrambots.meta.api.objects.User telegramUser;
        if (update.hasCallbackQuery()) {
            telegramUser = update.getCallbackQuery().getFrom();
        } else {
            telegramUser = update.getMessage().getFrom();
        }
        return getUser(telegramUser, chatId);
    }


    public User getUser(org.telegram.telegrambots.meta.api.objects.User telegramUser, Long chatId) {
        User user = getBySignature(telegramUser.getId().toString());
        if (user != null) {
            return user;
        }
        user = createUser(telegramUser, chatId);
        return user;

    }

    public User createUser(org.telegram.telegrambots.meta.api.objects.User telegramUser, Long chatId) {
        User user;
        user = new User();
        user.setBot(telegramUser.getBot());
        user.setSignature(telegramUser.getId().toString());
        user.setUserFirstName(telegramUser.getFirstName());
        user.setUserLastName(telegramUser.getLastName());
        user.setUserName(telegramUser.getUserName());
        user.setDateCreate(new Date());
        user.setPassDay(0);
        user.setChatId(chatId);
        save(user);
        return user;
    }

    public User updateUserResultByCurrentDay(User user, float pct) {
        Integer sysCurrentDay = propertiesService.getAsInteger(Properties.CURRENT_DAY);
        String result = String.valueOf(pct);
        if (sysCurrentDay == 1 && user.getDayOneResult() == null) {
            user.setDayOneResult(result);
        } else if (sysCurrentDay == 2 && user.getDayTwoResult() == null) {
            user.setDayTwoResult(result);
        } else {
            LOGGER.info("Данные не обновлены sysCurrentDay:{}, DayOneResult:{}, DayTwoResult:{}",
                sysCurrentDay, user.getDayOneResult(), user.getDayTwoResult());
        }
        user.setPassDay(sysCurrentDay);
        save(user);
        return user;
    }

    public List<User> getAll() {
        return userDao.findAll();
    }
}
