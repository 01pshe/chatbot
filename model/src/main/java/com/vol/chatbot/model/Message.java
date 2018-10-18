package com.vol.chatbot.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bot_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "nameUser")
    private String nameUser;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "message", nullable = false, length = 4096)
    private String messageText;

    @Column(name = "inbound")
    private Boolean inbound;

    public Message() {
        // Do nothing
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Boolean getInbound() {
        return inbound;
    }

    public void setInbound(Boolean inbound) {
        this.inbound = inbound;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + id +
            ", user=" + user +
            ", date=" + date +
            ", messageText='" + messageText + '\'' +
            '}';
    }
}
