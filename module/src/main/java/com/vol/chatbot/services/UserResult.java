package com.vol.chatbot.services;

import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.QuestionWeight;
import com.vol.chatbot.services.propertiesservice.PropertiesService;

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

    public float getPercentage() {
        return points * 100f / Constants.POINTS_MAX;
    }

    public String getResultMessageByCurrentDay(PropertiesService propertiesService) {
        String total;
        Properties prop = null;
        if (this.getPercentage() >= propertiesService.getAsFloat(Properties.RESULT_EXCELLENT_PCT)) {
            prop = Properties.EXCELLENT_RESULT;
            total = prop.getDefaultVal();
        }else if (this.getPercentage() >= propertiesService.getAsFloat(Properties.RESULT_GOOD_PCT)){
            prop = Properties.GOOD_RESULT;
            total = prop.getDefaultVal();
        }else if (this.getPercentage() >= propertiesService.getAsFloat(Properties.RESULT_BAD_PCT)){
            prop = Properties.BAD_RESULT;
            total = prop.getDefaultVal();
        }else {
            total = "Жизнь прекрасна, не печальтесь!";
        }
        return String.format(total, getPercentage());
    }
}
