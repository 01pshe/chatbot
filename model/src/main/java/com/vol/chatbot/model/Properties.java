package com.vol.chatbot.model;

public enum Properties {

    SuspendMode("false"),
    useDay("1"),
    timeQuestionRefresh("60");

    private String defaultVal;

    Properties(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getDefaultVal() {
        return defaultVal;
    }
}
