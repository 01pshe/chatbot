package com.vol.chatbot;

import com.vol.chatbot.model.ScenarioStep;
import com.vol.chatbot.services.ApplicationContextProvider;
import com.vol.chatbot.steps.StepExecutor;

import java.util.Map;
import java.util.TreeMap;

public class StepExecutorFactory {

    private static volatile StepExecutorFactory factory;

    private final Map<String, StepExecutor> objects = new TreeMap<>();

    public static StepExecutorFactory getFactory() {
        StepExecutorFactory localInstance = factory;
        if (localInstance == null) {
            synchronized (StepExecutorFactory.class) {
                localInstance = factory;
                if (localInstance == null) {
                    factory = localInstance = new StepExecutorFactory();
                }
            }
        }
        return localInstance;
    }


    public StepExecutor getStep(ScenarioStep step) {
        String executorClass = step.getStep().getExecutorClass();
        StepExecutor executor = objects.get(executorClass);
        if (executor == null) {
            synchronized (objects) {
                executor = objects.get(executorClass);
                if (executor == null) {
                    executor = (StepExecutor) ApplicationContextProvider.getContext().getBean(executorClass);
                    objects.put(executorClass, executor);
                }
            }
        }
        return executor;
    }
}
