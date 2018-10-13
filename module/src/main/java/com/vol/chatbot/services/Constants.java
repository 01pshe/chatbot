package com.vol.chatbot.services;

public class Constants {
    public static final int DAY_QUESTION_CNT = 3;
    public static final int DIFFICULT_QUESTION_CNT = 1;
    public static final int MEDIUM_QUESTION_CNT = 1;
    public static final int EASY_QUESTION_CNT = 1;

    public static final int DIFFICULT_POINT = 3;
    public static final int MEDIUM_POINT = 2;
    public static final int EASY_POINT = 1;

    public static final int POINTS_MAX = DIFFICULT_QUESTION_CNT * DIFFICULT_POINT +
        MEDIUM_QUESTION_CNT * MEDIUM_POINT +
        EASY_QUESTION_CNT * EASY_POINT;

}
