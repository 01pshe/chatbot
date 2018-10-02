package com.vol.chatbot.userRepository;

import com.vol.chatbot.model.UserInfo;

import java.util.List;

public interface UserInfoRepository {

  List<UserInfo> getAll();
}
