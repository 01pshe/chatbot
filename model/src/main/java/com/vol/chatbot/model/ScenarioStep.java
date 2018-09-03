package com.vol.chatbot.model;

import javax.persistence.*;

@Entity
@Table(name = "bot_scenariostep")
public class ScenarioStep {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "step_number", nullable = false)
    private Integer stepNumber;

    @ManyToOne
    @JoinColumn(name = "step_id")
    private Step step;

    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    public ScenarioStep() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(Integer stepNumber) {
        this.stepNumber = stepNumber;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public String toString() {
        return "ScenarioStep{" +
                "id=" + id +
                ", stepNumber=" + stepNumber +
                ", step=" + step +
                '}';
    }
}
