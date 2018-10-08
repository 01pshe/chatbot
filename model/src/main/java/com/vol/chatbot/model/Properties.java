package com.vol.chatbot.model;

public enum Properties {

    SuspendMode("false");

    private String defaultVal;

    Properties(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getDefaultVal() {
        return defaultVal;
    }
}
