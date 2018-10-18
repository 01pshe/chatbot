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

    @Column(name = "dateCreate")
    private Date dateCreate;

    @Column(name = "pass_day")
    private Integer passDay;

    @Column(name = "day_one_result", columnDefinition = "Decimal(10,2) default '00.00'")
    private float dayOneResult;

    @Column(name = "day_two_result", columnDefinition = "Decimal(10,2) default '00.00'")
    private float dayTwoResult;

    @Column(name = "total_result", columnDefinition = "Decimal(10,2) default '00.00'")
    private float totalResult;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "start_first")
    private boolean startFirst;

    @Column(name = "start_second")
    private boolean startSecond;

    public User() {
        // Do nothing
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPassDay() {
        return passDay;
    }

    public void setPassDay(Integer passDay) {
        this.passDay = passDay;
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

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public float getDayOneResult() {
        return dayOneResult;
    }

    public void setDayOneResult(float dayOneResult) {
        this.dayOneResult = dayOneResult;
    }

    public float getDayTwoResult() {
        return dayTwoResult;
    }

    public void setDayTwoResult(float dayTwoResult) {
        this.dayTwoResult = dayTwoResult;
    }

    public float getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(float totalResult) {
        this.totalResult = totalResult;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public boolean isStartFirst() {
        return startFirst;
    }

    public void setStartFirst(boolean startFirst) {
        this.startFirst = startFirst;
    }

    public boolean isStartSecond() {
        return startSecond;
    }

    public void setStartSecond(boolean startSecond) {
        this.startSecond = startSecond;
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
            ", dateCreate=" + dateCreate +
            ", chatId=" + chatId +
            '}';
    }


}
