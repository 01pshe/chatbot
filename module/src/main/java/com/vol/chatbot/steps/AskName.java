package com.vol.chatbot.steps;

import com.vol.chatbot.model.impl.Message;
import com.vol.chatbot.model.impl.User;
import com.vol.chatbot.model.impl.UserInfo;
import com.vol.chatbot.services.ScenarioService;
import com.vol.chatbot.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AskName extends AbstractStepExecutor {

    private static final String WELCOME_STRING = "Как Вас зовут?";

    private UserInfoService userInfoService;
    private ScenarioService scenarioService;

    @Autowired
    public AskName(UserInfoService userInfoService, ScenarioService scenarioService) {
        this.userInfoService = userInfoService;
        this.scenarioService = scenarioService;
    }

    @Override
    public String runStep(User user, Message message) {
        //заполним имя пользователя
        UserInfo userInfo = user.getUserInfo();
        userInfo.setName(message.getMessageText());
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
