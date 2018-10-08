package com.vol.chatbot.model.impl;

public enum Properties {

    SuspendMode("false"),
    UseDay("1");

    private String defaultVal;

    Properties(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getDefaultVal() {
        return defaultVal;
    }
}
