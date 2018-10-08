package com.vol.chatbot.model;

import com.vol.chatbot.model.impl.User;

public interface Answer {
    String getAnswer();

    void setAnswer(String answer);

    User getUser();

    void setUser(User user);

    Question getQuestion();

}
