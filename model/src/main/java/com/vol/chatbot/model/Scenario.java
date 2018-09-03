package com.vol.chatbot.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bot_scenario")
public class Scenario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "current_step")
    private Integer currentStepNumber;

    @OneToMany(mappedBy = "scenario", fetch = FetchType.EAGER)
    private Set<ScenarioStep> scenarioSteps;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private User user;

    public Scenario() {
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

    public Set<ScenarioStep> getScenarioSteps() {
        return scenarioSteps;
    }

    public void setScenarioSteps(Set<ScenarioStep> scenarioSteps) {
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
