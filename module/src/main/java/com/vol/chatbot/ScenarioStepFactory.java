package com.vol.chatbot;

import com.vol.chatbot.model.ScenarioStep;
import com.vol.chatbot.steps.AskNameRunStep;
import com.vol.chatbot.steps.RunStep;

public class ScenarioStepFactory {

    public static RunStep getStep(Long step, Long subStep){
        return new AskNameRunStep();
    }

    public static RunStep getStep(ScenarioStep step){
        return new AskNameRunStep();
    }
}
