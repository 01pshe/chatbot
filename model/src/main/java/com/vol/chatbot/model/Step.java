package com.vol.chatbot.model;

import javax.persistence.*;

@Entity
@Table(name = "bot_step")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "step")
    private Long step;

    @Column(name = "substep")
    private Long substep;

    @Column(name = "description")
    private String description;

    public Step() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public Long getSubstep() {
        return substep;
    }

    public void setSubstep(Long substep) {
        this.substep = substep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", step=" + step +
                ", substep=" + substep +
                ", description='" + description + '\'' +
                '}';
    }
}
