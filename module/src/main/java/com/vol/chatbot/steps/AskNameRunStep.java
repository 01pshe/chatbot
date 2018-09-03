package com.vol.chatbot.steps;

import com.vol.chatbot.ScenarioStepFactory;
import com.vol.chatbot.dao.ScenarioStepDao;
import com.vol.chatbot.dao.UserDao;
import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.ScenarioStep;
import com.vol.chatbot.model.Step;
import com.vol.chatbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AskNameRunStep implements RunStep {

    private String WELCOME_STRING = "Как вас зовут?";

    @Autowired
    private ScenarioStepDao scenarioStepDao;

    @Autowired
    private UserDao userDao;

    private ScenarioStep getNextStep(User user){
        List<ScenarioStep> steps = new ArrayList<>(user.getScenario().getScenarioSteps());
        steps.sort(Comparator.comparing(ScenarioStep::getStepNumber));
        ScenarioStep currentStep = scenarioStepDao.getByScenarioAndAndStepNumber(user.getScenario(),user.getScenario().getCurrentStepNumber());
        Integer nextStepIdx = steps.indexOf(currentStep)+1;
        if(steps.size()<=nextStepIdx+1){
            return steps.get(nextStepIdx);
        }
        return null;
    }

    @Override
    public String runStep(User user, Message message) {
        //заполнили имя пользователя
        user.getUserInfo().setName(message.getMessage());


        //теперь нужно показать привествие следующего шага и установить его текущим.
        ScenarioStep nextStep = getNextStep(user);
        user.getScenario().setCurrentStepNumber(nextStep.getStepNumber());
        String retVal = ScenarioStepFactory.getStep(nextStep).getWelcomeString();
        userDao.save(user);
        return retVal;
    }

    @Override
    public String getWelcomeString() {
        return WELCOME_STRING;
    }
}
