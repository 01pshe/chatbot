package com.vol.chatbot.services;

import com.vol.chatbot.model.QuestionWeight;

public class UserResult {

    private int difficultAnswer;
    private int mediumAnswer;
    private int easyAnswer;

    private int difficultCorrect;
    private int mediumCorrect;
    private int easyCorrect;

    private int points;

    public int getAnswerAll() {
        return difficultAnswer + mediumAnswer + easyAnswer;
    }

    public int getAnswerCorrectAll() {
        return difficultCorrect + mediumCorrect + easyCorrect;
    }

    public void incAnswer(QuestionWeight weight, Boolean isCorrect) {
        switch (weight) {
            case DIFFICULT:
                difficultAnswer++;
                if (isCorrect) {
                    difficultCorrect++;
                    points = points + Constants.DIFFICULT_POINT;
                }
                break;
            case MEDIUM:
                mediumAnswer++;
                if (isCorrect) {
                    mediumCorrect++;
                    points = points + Constants.MEDIUM_POINT;
                }
                break;
            case EASY:
                easyAnswer++;
                if (isCorrect) {
                    easyCorrect++;
                    points = points + Constants.EASY_POINT;
                }
                break;
        }
    }


    public Double getPercent() {

        return 0.0;
    }


}
