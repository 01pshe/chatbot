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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getDateUserAnswer() {
        return dateUserAnswer;
    }

    public void setDateUserAnswer(Date dateUserAnswer) {
        this.dateUserAnswer = dateUserAnswer;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
}
