package com.vol.chatbot.model;

import com.vol.chatbot.model.Question;

import javax.persistence.*;

@Entity
@Table(name = "bot_question")
public class Question {

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

    @Column(name = "answerA", unique = false, nullable = true, length = 60)
    private String answerA;

    @Column(name = "answerB", unique = false, nullable = true, length = 60)
    private String answerB;

    @Column(name = "answerC", unique = false, nullable = true, length = 60)
    private String answerC;

    @Column(name = "answerD", unique = false, nullable = true, length = 60)
    private String answerD;

    @Column(name = "answerR", unique = false, nullable = true, length = 60)
    private String answerR;

    @Column(name = "answerRight", unique = false, nullable = true, length = 60)
    private String answerRight;

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public QuestionWeight getWeight() {
        return weight;
    }

    public Integer getUseDay() {
        return useDay;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    @Override
    public String toString() {
        return "Question{" +
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
