package com.vol.chatbot.web.properties;

import com.vol.chatbot.dao.PropertiesDao;
import com.vol.chatbot.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertiesProcessorImpl implements PropertiesProcessor {

    private PropertiesDao propertiesDao;

    @Autowired
    public PropertiesProcessorImpl(PropertiesDao propertiesDao) {
        this.propertiesDao = propertiesDao;
    }


    @Override
    public List<Property> getAll() {
        return propertiesDao.findAll();
    }

    @Override
    public void saveList(List<Property> properties) {
        propertiesDao.saveAll(properties);
    }

    @Override
    public void save(Property prop) {

    }
}
