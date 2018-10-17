package com.vol.chatbot.web.buttons;

import com.vol.chatbot.dao.PropertiesDao;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ButtonProcessorImpl implements ButtonProcessor {

    private PropertiesDao propertiesDao;

    @Autowired
    public ButtonProcessorImpl(PropertiesDao propertiesDao) {
        this.propertiesDao = propertiesDao;
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
    }
}
