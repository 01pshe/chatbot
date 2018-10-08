package com.vol.chatbot.model.impl;

import com.vol.chatbot.model.Question;

import javax.persistence.*;

@Entity
@Table(name = "bot_question")
public class QuestionImpl implements Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "question", unique = true, nullable = false, length = 4096)
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "weight", length = 9, unique = false, nullable = false)
    private QuestionWeight weight;

    @Column(name = "useDay", unique = false, nullable = true)
    private Integer useDay;

    @Column(name = "answerA", unique = false, nullable = true, length = 2048)
    private String answerA;

    @Column(name = "answerB", unique = false, nullable = true, length = 2048)
    private String answerB;

    @Column(name = "answerC", unique = false, nullable = true, length = 2048)
    private String answerC;

    @Column(name = "answerD", unique = false, nullable = true, length = 2048)
    private String answerD;

    @Column(name = "answerR", unique = false, nullable = true, length = 2048)
    private String answerR;

    @Column(name = "answerRight", unique = false, nullable = true, length = 2048)
    private String answerRight;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public QuestionWeight getWeight() {
        return weight;
    }

    @Override
    public Integer getUseDay() {
        return useDay;
    }

    @Override
    public String getAnswerA() {
        return answerA;
    }

    @Override
    public String getAnswerB() {
        return answerB;
    }

    @Override
    public String getAnswerC() {
        return answerC;
    }

    @Override
    public String getAnswerD() {
        return answerD;
    }

    @Override
    public String toString() {
        return "QuestionImpl{" +
            "id=" + id +
            ", question='" + question + '\'' +
            ", weight=" + weight +
            ", useDay=" + useDay +
            ", answerA='" + answerA + '\'' +
            ", answerB='" + answerB + '\'' +
            ", answerC='" + answerC + '\'' +
            ", answerD='" + answerD + '\'' +
            '}';
    }

}
