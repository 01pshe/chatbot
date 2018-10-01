package com.vol.chatbot.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bot_scenario")
public class Scenario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_step")
    private Integer currentStepNumber;

    @OneToMany(mappedBy = "scenario", fetch = FetchType.EAGER)
    @OrderBy(value = "stepNumber")
    private List<ScenarioStep> scenarioSteps;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    public Scenario() {
        // Do nothing
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurrentStepNumber() {
        return currentStepNumber;
    }

    public void setCurrentStepNumber(Integer currentStepNumber) {
        this.currentStepNumber = currentStepNumber;
    }

    public List<ScenarioStep> getScenarioSteps() {
        return scenarioSteps;
    }

    public void setScenarioSteps(List<ScenarioStep> scenarioSteps) {
        this.scenarioSteps = scenarioSteps;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Scenario{" +
            "id=" + id +
            ", currentStepNumber=" + currentStepNumber +
            ", scenarioSteps=" + scenarioSteps +
            '}';
    }
}
