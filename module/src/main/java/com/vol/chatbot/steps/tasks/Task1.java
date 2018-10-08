package com.vol.chatbot.steps.tasks;

import com.vol.chatbot.model.impl.Message;
import com.vol.chatbot.model.impl.User;
import com.vol.chatbot.services.ScenarioService;
import com.vol.chatbot.steps.AbstractStepExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Task1 extends AbstractStepExecutor {

    private static final String FIRST_TASK = "Это <strong>первая задача</strong>, которую необходимо решить!";

    private ScenarioService scenarioService;

    @Autowired
    public Task1(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @Override
    protected String runStep(User user, Message message) {
        return FIRST_TASK;
    }

    @Override
    public String getWelcomeString() {
        return "<strong>Task1</strong>";
    }

    @Override
    protected ScenarioService getScenarioService() {
        return scenarioService;
    }
}
