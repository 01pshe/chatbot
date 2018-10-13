package com.vol.chatbot.knowledge;

import com.vol.chatbot.services.Constants;
import com.vol.chatbot.services.QuestionService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuestionServiceTest {

    @Test
    public void countQuestion() {
        assertEquals(Constants.DAY_QUESTION_CNT,
            (Constants.DIFFICULT_QUESTION_CNT + Constants.EASY_QUESTION_CNT + Constants.MEDIUM_QUESTION_CNT));
    }

}