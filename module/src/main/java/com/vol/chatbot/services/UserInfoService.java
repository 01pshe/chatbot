package com.vol.chatbot.services;

import com.vol.chatbot.dao.UserInfoDao;
import com.vol.chatbot.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoService {

    private UserInfoDao userInfoDao;

    @Autowired
    public UserInfoService(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public void save(UserInfo userInfo){
        userInfoDao.save(userInfo);
    }
}
