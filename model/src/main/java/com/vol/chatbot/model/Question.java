package com.vol.chatbot.model;

import javax.persistence.*;

@Entity
@Table(name = "bot_question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "question", unique = true, nullable = false, length = 2048)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "weight", length = 9, unique = true, nullable = false)
    private QuestionWeight weight;

    @Column(name = "useDay", unique = true, nullable = true)
    private Integer useDay;

    @Column(name = "answerA", unique = false, nullable = true)
    private String answerA;

    @Column(name = "answerB", unique = false, nullable = true)
    private String answerB;

    @Column(name = "answerC", unique = false, nullable = true)
    private String answerC;

    @Column(name = "answerD", unique = false, nullable = true)
    private String answerD;

    @Column(name = "answerR", unique = false, nullable = true)
    private String answerR;

    @Column(name = "answerRight", unique = false, nullable = true)
    private String answerRight;

}
