package com.vol.chatbot.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bot_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "signature", unique = true, nullable = false)
    private String signature;

    @Column(name = "userfirstname")
    private String userFirstName;

    @Column(name = "userlastname")
    private String userLastName;

    @Column(name = "username")
    private String userName;

    @Column(name = "bot")
    private boolean bot;

    @Column(name = "datecreate")
    private Date datecreate;

    @Column(name = "pass_day")
    private Integer passDay;

    public User() {
        // Do nothing
    }

    public Long getId() {
        return id;
    }

    public Integer getPassDay() {
        return passDay;
    }

    public void setPassDay(Integer passDay) {
        this.passDay = passDay;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public Date getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(Date datecreate) {
        this.datecreate = datecreate;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", signature='" + signature + '\'' +
            ", userFirstName='" + userFirstName + '\'' +
            ", userLastName='" + userLastName + '\'' +
            ", userName='" + userName + '\'' +
            ", bot=" + bot +
            ", datecreate=" + datecreate +
            '}';
    }


}
