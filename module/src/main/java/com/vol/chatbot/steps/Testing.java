package com.vol.chatbot.steps;

import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.User;
import com.vol.chatbot.services.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Testing extends AbstractStepExecutor {

    private static final String OUTPUT_STRING = "Итак, давай начнем тестирование!\n";

    private ScenarioService scenarioService;

    @Autowired
    public Testing(ScenarioService scenarioService) {
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
