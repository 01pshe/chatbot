package com.vol.chatbot.model;

import javax.persistence.*;

@Entity
@Table(name = "bot_question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "text", unique = true, nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "weight", length = 9, unique = true, nullable = false)
    private QuestionWeight weight;

    @Column(name = "day", unique = true, nullable = false)
    private Integer day;

    @Column(name = "answer1", unique = false, nullable = true)
    private String answer1;

    @Column(name = "answer2", unique = false, nullable = true)
    private String answer2;

    @Column(name = "answer3", unique = false, nullable = true)
    private String answer3;

    @Column(name = "answer4", unique = false, nullable = true)
    private String answer4;

    @Column(name = "answerRight", unique = false, nullable = false)
    private String answerRight;

}
