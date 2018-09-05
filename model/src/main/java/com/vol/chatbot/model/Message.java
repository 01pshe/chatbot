package com.vol.chatbot.model;

import javax.persistence.*;

@Entity
@Table(name = "bot_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //new Date(TimeUnit.MILLISECONDS.convert(1536070445L,TimeUnit.SECONDS))
    @Column(name="date", nullable=false)
    private Long date;

    @Column(name="message", nullable=false)
    private String message;

    @Column(name="inbound")
    private Boolean inbound;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getInbound() {
        return inbound;
    }

    public void setInbound(Boolean inbound) {
        this.inbound = inbound;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
