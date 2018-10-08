package com.vol.chatbot.steps;

import com.vol.chatbot.StepExecutorFactory;
import com.vol.chatbot.model.impl.Message;
import com.vol.chatbot.model.impl.Scenario;
import com.vol.chatbot.model.impl.ScenarioStep;
import com.vol.chatbot.model.impl.User;
import com.vol.chatbot.services.ScenarioService;

public abstract class AbstractStepExecutor implements StepExecutor {

    protected abstract String runStep(User user, Message message);

    protected abstract ScenarioService getScenarioService();

    @Override
    public String run(User user, Message message) {
        StringBuilder retVal = new StringBuilder();
        Scenario currentScenario = user.getScenario();
        ScenarioStep scenarioStep = getScenarioService().getCurrentStep(currentScenario);
        boolean flag = true;
        while (flag || !scenarioStep.getStep().getNeedAnswer()) {
            String res = runStep(user, message);
            retVal.append(res);
            //теперь нужно показать привествие следующего шага и установить его текущим.
            ScenarioStep nextStep = getScenarioService().getNextStep(currentScenario);
            currentScenario.setCurrentStepNumber(nextStep.getStepOrder());
            retVal.append(nextStep.getStepOrder());
            retVal.append(". ");
            retVal.append(StepExecutorFactory.getFactory().getStep(nextStep).getWelcomeString());
            retVal.append("\n");
            getScenarioService().save(currentScenario);
            scenarioStep = getScenarioService().getCurrentStep(currentScenario);
            flag = false;
        }
        return retVal.toString();
    }
}
