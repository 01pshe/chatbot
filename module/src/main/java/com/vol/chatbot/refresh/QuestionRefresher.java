package com.vol.chatbot.refresh;

import com.vol.chatbot.dao.QuestionDao;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.services.UserService;
import com.vol.chatbot.services.propertiesservice.PropertiesService;

import java.util.List;

public class QuestionRefresher implements Runnable {

    private List<Question> questions;
    private QuestionDao questionDao;
    private UserService userService;
    private PropertiesService propertiesService;


    @Override
    public void run() {
        this.questions = questionDao.findQuestionByUseDay(propertiesService.getAsInteger(Properties.CURRENT_DAY));

    }
}
