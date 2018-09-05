package com.vol.chatbot.services;

import com.vol.chatbot.dao.ScenarioDao;
import com.vol.chatbot.dao.ScenarioStepDao;
import com.vol.chatbot.model.Scenario;
import com.vol.chatbot.model.ScenarioStep;
import com.vol.chatbot.model.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class ScenarioService {

    private ScenarioDao scenarioDao;
    private ScenarioStepDao scenarioStepDao;

    @Autowired
    public ScenarioService(ScenarioDao scenarioDao, ScenarioStepDao scenarioStepDao) {
        this.scenarioDao = scenarioDao;
        this.scenarioStepDao = scenarioStepDao;
    }

    public void save(Scenario scenario){
        scenarioDao.save(scenario);
    }

    public ScenarioStep getCurrentStep(Scenario scenario){
        return scenarioStepDao.getByScenarioAndAndStepNumber(scenario,scenario.getCurrentStepNumber());
    }


    public static<T extends ScenarioStep>  Integer find(List<T> a, T target)
    {
        return IntStream.range(0, a.size())
                .filter(i -> target.getId().equals(a.get(i).getId()))
                .findFirst()
                .orElse(-1);	// return -1 if target is not found
    }

    public ScenarioStep getNextStep(Scenario scenario){
        List<ScenarioStep> steps = scenario.getScenarioSteps();
        steps.sort(Comparator.comparing(ScenarioStep::getStepNumber));

        ScenarioStep currentStep = getCurrentStep(scenario);
        Integer currentStepIdx = find(steps,currentStep);
        if (currentStepIdx>=0){
            Integer nextStepIdx = currentStepIdx+1;

            if (steps.size()>nextStepIdx){
                return steps.get(nextStepIdx);
            } else{
                return currentStep;
            }
        } else{
            return currentStep;
        }


    }
}
