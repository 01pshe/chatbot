package com.vol.chatbot.services.propertiesservice;

import com.vol.chatbot.model.Properties;

public interface PropertiesService {

    Boolean getAsBoolean(Properties propertyName);

    String getAsString(Properties propertyName);

    Long getAsLong(Properties propertyName);

    Integer getAsInteger(Properties propertyName);

    Float getAsFloat(Properties propertyName);

    void refreshProperties();

}
