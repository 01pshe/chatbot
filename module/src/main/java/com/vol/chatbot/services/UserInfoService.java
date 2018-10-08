package com.vol.chatbot.services;

import com.vol.chatbot.dao.UserInfoDao;
import com.vol.chatbot.model.impl.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoService.class);

    private UserInfoDao userInfoDao;

    @Autowired
    public UserInfoService(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public void save(UserInfo userInfo) {
        LOGGER.trace("Saving UserInfo id= {}.", userInfo.getId());
        userInfoDao.save(userInfo);
        LOGGER.trace("UserInfo saved.");
    }
}
