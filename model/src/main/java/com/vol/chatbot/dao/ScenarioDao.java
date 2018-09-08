package com.vol.chatbot.dao;

import com.vol.chatbot.model.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioDao extends JpaRepository<Scenario, Long> {
}
