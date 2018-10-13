package com.vol.chatbot.services;

import com.google.common.util.concurrent.RateLimiter;
import com.vol.chatbot.dao.QuestionDao;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.services.propertiesservice.PropertiesService;

import java.util.List;

public class QuestionRefresher implements Runnable {

    private final Object lockObj = new Object();
    private double tps;
    private PropertiesService propertiesService;
    private QuestionDao questionDao;
    private List<Question> questions;

    public QuestionRefresher(PropertiesService propertiesService, QuestionDao questionDao, List<Question> questions) {
        this.propertiesService = propertiesService;
        this.questionDao = questionDao;
        this.questions = questions;
    }

    public void setTps(double tps) {
        this.tps = tps;
    }

    @Override
    public void run() {

    }

    private void refresh() {
        synchronized (lockObj) {
            this.questions = questionDao.findQuestionByUseDay(propertiesService.getAsInteger(Properties.CURRENT_DAY));
        }
    }


}
