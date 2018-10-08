package com.vol.chatbot.model;

import com.vol.chatbot.model.impl.QuestionWeight;

public interface Question {
    Long getId();

    String getQuestion();

    QuestionWeight getWeight();

    Integer getUseDay();

    String getAnswerA();

    String getAnswerB();

    String getAnswerC();

    String getAnswerD();

}
