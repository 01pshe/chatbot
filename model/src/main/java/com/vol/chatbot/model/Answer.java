package com.vol.chatbot.model;

import javax.persistence.*;

@Entity
@Table(name = "bot_answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "id", unique = false, nullable = false)
    private String text;

    @Column(name = "user_id", unique = false, nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "question_id", unique = false, nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Question question;

}
