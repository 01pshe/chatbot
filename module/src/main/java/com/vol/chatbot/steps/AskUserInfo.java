package com.vol.chatbot.steps;

import com.vol.chatbot.model.impl.Message;
import com.vol.chatbot.model.impl.User;
import com.vol.chatbot.services.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AskUserInfo extends AbstractStepExecutor {

    private static final String OUTPUT_STRING = "Приветствую тебя в нашем тесте!\n Давай знакомиться\n";

    private ScenarioService scenarioService;

    @Autowired
    public AskUserInfo(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @Override
    protected String runStep(User user, Message message) {
        return OUTPUT_STRING;
    }

    @Override
    public String getWelcomeString() {
        return "";
    }

    @Override
    protected ScenarioService getScenarioService() {
        return scenarioService;
    }
}
