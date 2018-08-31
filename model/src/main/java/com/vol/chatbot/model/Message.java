package com.vol.chatbot.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bot_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long sysId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Column(name="date", nullable=false)
    private Date date;

    @Column(name="message", nullable=false)
    private String message;

    public Message() {
    }


    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sysId=" + sysId +
                ", user=" + user +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
