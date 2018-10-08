package com.vol.chatbot.model.impl;

import com.vol.chatbot.model.Answer;
import com.vol.chatbot.model.Question;

import javax.persistence.*;

@Entity
@Table(name = "bot_answer")
public class AnswerImpl implements Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "answer", unique = false, nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
