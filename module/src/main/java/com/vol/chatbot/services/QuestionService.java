package com.vol.chatbot.services;

import com.vol.chatbot.dao.QuestionDao;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.QuestionWeight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    public static final int DAY_QUESTION_CNT = 20;
    public static final int DIFFICULT_QUESTION_CNT = 5;
    public static final int MEDIUM_QUESTION_CNT = 7;
    public static final int EASY_QUESTION_CNT = 8;
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);
    private List<Question> questions;

    @Autowired
    public QuestionService(QuestionDao questionDao) {
        this.questions = questionDao.findAll();
    }

    Question getQuestion(AnswerHelper helper) {

        Set<Long> questionIds = new HashSet<>();
        int dCnt = 0;
        int mCnt = 0;
        int eCnt = 0;

        for (Question passedQuestion : helper.getPassedQuestions()) {
            questionIds.add(passedQuestion.getId());
            switch (passedQuestion.getWeight()) {
                case EASY:
                    eCnt++;
                    break;
                case MEDIUM:
                    mCnt++;
                    break;
                case DIFFICULT:
                    dCnt++;
                    break;
                default:
                    LOGGER.warn("Не определена сложность у вопрос id: {}", passedQuestion.getId());
            }
        }

        QuestionWeight weight = getWeightCurrent(dCnt, mCnt, eCnt);
        int currentDay = LocalDate.now().getDayOfMonth();
        LOGGER.info("\nДля ползователя: {},\n   DayOfMonth: {},\n   текущая сложность: {},\n   получено ответов {}\n",
            helper.getUser().getUserFirstName(), currentDay, weight, questionIds.size());

        List<Question> batch = questions.stream()
            .filter(question -> question.getWeight() == weight && question.getUseDay() == currentDay && !questionIds.contains(question.getId()))
            .collect(Collectors.toList());

        Question returnQuestion = null;
        if (!batch.isEmpty()) {
            Collections.shuffle(batch);
            returnQuestion = batch.get(0);
        }

        return returnQuestion;

    }

    private QuestionWeight getWeightCurrent(int dCnt, int mCnt, int eCnt) {
        QuestionWeight weight = null;
        if (eCnt < EASY_QUESTION_CNT) {
            weight = QuestionWeight.EASY;
        } else if (mCnt < MEDIUM_QUESTION_CNT) {
            weight = QuestionWeight.MEDIUM;
        } else if (dCnt < DAY_QUESTION_CNT) {
            weight = QuestionWeight.DIFFICULT;
        }
        return weight;
    }

}
