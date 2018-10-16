package com.vol.chatbot.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bot_answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "user_answer")
    private String userAnswer;

    @Column(name = "day_answer")
    private Integer dayAnswer;

    @Column(name = "result")
    private String result;

    @Column(name = "dateUserAnswer")
    private Date dateUserAnswer;

    @Column(name = "dateCreate")
    private Date dateCreate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Enumerated(EnumType.STRING)
    @Column(name = "weight", length = 9, nullable = false)
    private QuestionWeight weight;

    @Column(name = "user_name")
    private String userName;

    public Long getId() {
        return id;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Integer getDayAnswer() {
        return dayAnswer;
    }

    public void setDayAnswer(Integer dayAnswer) {
        this.dayAnswer = dayAnswer;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setDateUserAnswer(Date dateUserAnswer) {
        this.dateUserAnswer = dateUserAnswer;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setWeight(QuestionWeight weight) {
        this.weight = weight;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
