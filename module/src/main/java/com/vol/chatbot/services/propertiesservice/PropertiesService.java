package com.vol.chatbot.services.propertiesservice;

import com.vol.chatbot.model.impl.Properties;

public interface PropertiesService {

    Boolean getAsBoolean(Properties propertyName);

    String getAsString(Properties propertyName);

    Long getAsLong(Properties propertyName);

}
