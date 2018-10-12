package com.vol.chatbot.services;

import com.vol.chatbot.Utils;
import com.vol.chatbot.dao.QuestionDao;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.QuestionWeight;
import com.vol.chatbot.services.propertiesservice.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);
    private List<Question> questions;
    private Thread refresherThread;

    @Autowired
    public QuestionService(QuestionDao questionDao, PropertiesService propertiesService) {

        this.questions = questionDao.findQuestionByUseDay(propertiesService.getAsInteger(Properties.CURRENT_DAY));
        QuestionRefresher refresher  = new QuestionRefresher(propertiesService,questionDao,questions);
        refresher.setTps((propertiesService.getAsInteger(Properties.REFRESH_QUESTION_TIME)));
        refresherThread = new Thread(refresher);
        refresherThread.start();

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
        LOGGER.info("\nДля ползователя: {},\n   DayOfMonth: {},\n   текущая сложность: {},\n   получено ответов {}\n",
            helper.getUser().getUserFirstName(), helper.getSysCurrentDay(), weight, questionIds.size());

        List<Question> batch = questions.stream()
            .filter(question -> question.getWeight() == weight && question.getUseDay() == helper.getSysCurrentDay() && !questionIds.contains(question.getId()))
            .collect(Collectors.toList());

        Question returnQuestion = null;
        if (!batch.isEmpty()) {
            returnQuestion = batch.get(Utils.getRandomInt(batch.size()));
        } else {
            LOGGER.info("Вопросы закончились batch.isEmpty()");
        }

        return returnQuestion;

    }

    private QuestionWeight getWeightCurrent(int dCnt, int mCnt, int eCnt) {
        QuestionWeight weight = null;
        if (eCnt < Constants.EASY_QUESTION_CNT) {
            weight = QuestionWeight.EASY;
        } else if (mCnt < Constants.MEDIUM_QUESTION_CNT) {
            weight = QuestionWeight.MEDIUM;
        } else if (dCnt < Constants.DAY_QUESTION_CNT) {
            weight = QuestionWeight.DIFFICULT;
        }
        return weight;
    }

}
