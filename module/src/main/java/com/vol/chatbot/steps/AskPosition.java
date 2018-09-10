package com.vol.chatbot.steps;

import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.User;
import com.vol.chatbot.model.UserInfo;
import com.vol.chatbot.services.ScenarioService;
import com.vol.chatbot.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AskPosition extends AbstractStepExecutor {

  private static final String WELCOME_STRING = "Какая у Вас должность?";

  private UserInfoService userInfoService;
  private ScenarioService scenarioService;

  @Autowired
  public AskPosition(UserInfoService userInfoService, ScenarioService scenarioService) {
    this.userInfoService = userInfoService;
    this.scenarioService = scenarioService;
  }

  @Override
  public String runStep(User user, Message message) {
    //заполним имя пользователя
    UserInfo userInfo = user.getUserInfo();
    userInfo.setPosition(message.getMessage());
    userInfoService.save(userInfo);
    return "";
  }

  @Override
  public String getWelcomeString() {
    return WELCOME_STRING;
  }

  @Override
  protected ScenarioService getScenarioService() {
    return scenarioService;
  }
}
