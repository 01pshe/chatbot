package com.vol.chatbot.restService;

import com.vol.chatbot.model.UserInfo;
import com.vol.chatbot.userRepository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestUserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestUserService.class);

  private final UserInfoRepository userInfoRepository;


  @Autowired
  public RestUserService(UserInfoRepository userInfoRepository) {
    this.userInfoRepository = userInfoRepository;
  }

  public List<UserInfo> getAll() {
    LOGGER.trace("Get all users");
    return userInfoRepository.getAll();
  }

}
