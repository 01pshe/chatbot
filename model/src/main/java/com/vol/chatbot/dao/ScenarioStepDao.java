package com.vol.chatbot.dao;

import com.vol.chatbot.model.impl.Scenario;
import com.vol.chatbot.model.impl.ScenarioStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioStepDao extends JpaRepository<ScenarioStep, Long> {

    ScenarioStep getByScenarioAndStepOrder(Scenario scenario, Integer stepNumber);
}
