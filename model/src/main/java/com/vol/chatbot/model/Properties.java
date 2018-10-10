package com.vol.chatbot.model;

public enum Properties {

    SUSPEND_MODE("false"),
    CURRENT_DAY("1"),
    REFRESH_QUESTION_TIME("60");


    private String defaultVal;

    Properties(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getDefaultVal() {
        return defaultVal;
    }
}
