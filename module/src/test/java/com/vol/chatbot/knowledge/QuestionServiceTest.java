package com.vol.chatbot.knowledge;

import com.vol.chatbot.model.Properties;
import com.vol.chatbot.services.Constants;
import com.vol.chatbot.services.QuestionService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuestionServiceTest {

    @Test
    public void countQuestion() {
        Integer expected = Integer.valueOf(Properties.DAY_QUESTION_CNT.getDefaultVal());
        Integer actual = Integer.valueOf(Properties.DIFFICULT_QUESTION_CNT.getDefaultVal()) +
            Integer.valueOf(Properties.EASY_QUESTION_CNT.getDefaultVal()) +
            Integer.valueOf(Properties.MEDIUM_QUESTION_CNT.getDefaultVal());

        assertEquals(expected,actual);
    }

}