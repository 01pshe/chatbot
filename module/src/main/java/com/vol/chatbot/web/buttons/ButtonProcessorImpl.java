package com.vol.chatbot.web.buttons;

import com.vol.chatbot.dao.PropertiesDao;
import com.vol.chatbot.messagesender.MessageSender;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.Property;
import com.vol.chatbot.services.QuestionService;
import com.vol.chatbot.services.propertiesservice.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ButtonProcessorImpl implements ButtonProcessor {

    private PropertiesDao propertiesDao;
    private QuestionService questionService;
    private MessageSender messageSender;
    private PropertiesService propertiesService;

    @Autowired
    public ButtonProcessorImpl(PropertiesDao propertiesDao,
                               QuestionService questionService,
                               MessageSender messageSender,
                               PropertiesService propertiesService) {
        this.propertiesDao = propertiesDao;
        this.questionService = questionService;
        this.messageSender = messageSender;
        this.propertiesService = propertiesService;
    }

    @Override
    public void setSuspendMode() {
        Optional<Property> suspendModeProp = propertiesDao.getByPropertyName(Properties.SUSPEND_MODE);
        Property prop;
        if (suspendModeProp.isPresent()){
            prop = suspendModeProp.get();
            prop.setPropertyValue("true");
        } else {
            prop = new Property();
            prop.setPropertyName(Properties.SUSPEND_MODE);
            prop.setPropertyValue("true");
        }
        propertiesDao.save(prop);
    }

    @Override
    public void unsetSuspendMode() {
        Optional<Property> suspendModeProp = propertiesDao.getByPropertyName(Properties.SUSPEND_MODE);
        Property prop;
        if (suspendModeProp.isPresent()){
            prop = suspendModeProp.get();
            prop.setPropertyValue("false");
        } else {
            prop = new Property();
            prop.setPropertyName(Properties.SUSPEND_MODE);
            prop.setPropertyValue("false");
        }
        propertiesDao.save(prop);
    }

    @Override
    public void startSecondDay() {
        Optional<Property> currentDayProp = propertiesDao.getByPropertyName(Properties.CURRENT_DAY);
        Property prop;
        if (currentDayProp.isPresent()){
            prop = currentDayProp.get();
            prop.setPropertyValue("2");
        } else {
            prop = new Property();
            prop.setPropertyName(Properties.CURRENT_DAY);
            prop.setPropertyValue("2");
        }
        propertiesDao.save(prop);
        propertiesService.refreshProperties();
        questionService.refreshQuestions(2);
        unsetSuspendMode();
        messageSender.sendAll(propertiesService.getAsString(Properties.NEW_DAY_MESSAGE));

    }
}
