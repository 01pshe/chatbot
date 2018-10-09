package com.vol.chatbot.services;

import com.vol.chatbot.dao.ScenarioDao;
import com.vol.chatbot.dao.ScenarioStepDao;
import com.vol.chatbot.model.Scenario;
import com.vol.chatbot.model.ScenarioStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class ScenarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioService.class);

    private ScenarioDao scenarioDao;
    private ScenarioStepDao scenarioStepDao;

    @Autowired
    public ScenarioService(ScenarioDao scenarioDao, ScenarioStepDao scenarioStepDao) {
        this.scenarioDao = scenarioDao;
        this.scenarioStepDao = scenarioStepDao;
    }

    public static <T extends ScenarioStep> Integer find(List<T> a, T target) {
        return IntStream.range(0, a.size())
            .filter(i -> target.getId().equals(a.get(i).getId()))
            .findFirst()
            .orElse(-1);  // return -1 if target is not found
    }

    public void save(Scenario scenario) {
        LOGGER.trace("Saving Scenario id= {}.", scenario.getId());
        scenarioDao.save(scenario);
        LOGGER.trace("Scenario saved.");
    }

    public ScenarioStep getCurrentStep(Scenario scenario) {
        ScenarioStep scenarioStep = scenarioStepDao.getByScenarioAndStepOrder(scenario, scenario.getCurrentStepNumber());
        LOGGER.trace("got current step");
        return scenarioStep;

    }

    public ScenarioStep getNextStep(Scenario scenario) {
        LOGGER.trace("getNextStep by scenario id={}", scenario.getId());
        List<ScenarioStep> steps = scenario.getScenarioSteps();
        steps.sort(Comparator.comparing(ScenarioStep::getStepOrder));

        ScenarioStep currentStep = getCurrentStep(scenario);
        Integer currentStepIdx = find(steps, currentStep);
        if (currentStepIdx >= 0) {
            Integer nextStepIdx = currentStepIdx + 1;

            if (steps.size() > nextStepIdx) {
                logGotNextStep();
                return steps.get(nextStepIdx);
            } else {
                logGotNextStep();
                return currentStep;
            }
        } else {
            logGotNextStep();
            return currentStep;
        }


    }

    private void logGotNextStep() {
        LOGGER.trace("gotNextStep");
    }
}
