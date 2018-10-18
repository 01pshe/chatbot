package com.vol.chatbot.services;

import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.QuestionWeight;
import com.vol.chatbot.services.propertiesservice.PropertiesService;

public class UserResult {
    private String userName;
    private int difficultAnswer;
    private int mediumAnswer;
    private int easyAnswer;

    private int difficultCorrect;
    private int mediumCorrect;
    private int easyCorrect;

    private int points;
    private Float pct;

    public UserResult(String userName) {
        this.userName = userName;
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

    public float getPercentage(PropertiesService propertiesService) {
        if (pct == null) {
            int pintMax =
                propertiesService.getAsInteger(Properties.DIFFICULT_QUESTION_CNT) * Constants.DIFFICULT_POINT +
                    propertiesService.getAsInteger(Properties.MEDIUM_QUESTION_CNT) * Constants.MEDIUM_POINT +
                    propertiesService.getAsInteger(Properties.EASY_QUESTION_CNT) * Constants.EASY_POINT;
            pct = points * 100f / pintMax;
        }
        return pct;
    }

    public String getResultMessageByCurrentDay(PropertiesService propertiesService) {
        String total;
        Properties prop;

        float currentPCT = this.getPercentage(propertiesService);

        if (currentPCT >= propertiesService.getAsFloat(Properties.RESULT_EXCELLENT_PCT)) {
            prop = Properties.EXCELLENT_RESULT;
            total = prop.getDefaultVal();
        } else if (currentPCT >= propertiesService.getAsFloat(Properties.RESULT_GOOD_PCT)) {
            prop = Properties.GOOD_RESULT;
            total = prop.getDefaultVal();
        } else if (currentPCT >= propertiesService.getAsFloat(Properties.RESULT_BAD_PCT)) {
            prop = Properties.BAD_RESULT;
            total = prop.getDefaultVal();
        } else {
            total = "Жизнь прекрасна, не печальтесь!";
        }
        return String.format(total, this.userName, currentPCT);
    }
}
