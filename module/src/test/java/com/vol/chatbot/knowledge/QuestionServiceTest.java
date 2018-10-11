package com.vol.chatbot.knowledge;

import com.vol.chatbot.services.QuestionService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuestionServiceTest {

    @Test
    public void countQuestion() {
        assertEquals(QuestionService.DAY_QUESTION_CNT,
            (QuestionService.DIFFICULT_QUESTION_CNT + QuestionService.EASY_QUESTION_CNT + QuestionService.MEDIUM_QUESTION_CNT));
    }

}