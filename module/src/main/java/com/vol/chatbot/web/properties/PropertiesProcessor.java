package com.vol.chatbot.web.properties;

import com.vol.chatbot.model.Property;

import java.util.List;

public interface PropertiesProcessor {

    List<Property> getAll();

    void saveList(List<Property> properties);

    void save(Property prop);

}
