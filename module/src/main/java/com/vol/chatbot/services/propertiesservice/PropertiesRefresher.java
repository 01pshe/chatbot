package com.vol.chatbot.services.propertiesservice;

import com.google.common.util.concurrent.RateLimiter;
import com.vol.chatbot.dao.PropertiesDao;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.Property;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PropertiesRefresher implements Runnable {

    private PropertiesDao propertiesDao;

    private double tps;

    private Map<Properties, String> currentProperties;

    public PropertiesRefresher(PropertiesDao propertiesDao, Map<Properties, String> currentProperties) {
        this.propertiesDao = propertiesDao;
        this.currentProperties = currentProperties;
    }

    public void setTps(double tps) {
        this.tps = tps;
    }

    @Override
    public void run() {
        final RateLimiter rateLimiter = RateLimiter.create(this.tps);
        while (true) {
            List<Property> actualProperties = propertiesDao.findAll();
            Map<Properties, String> actualMap = new EnumMap<>(Properties.class);
            for (Property prop : actualProperties) {
                actualMap.put(prop.getPropertyName(), prop.getPropertyValue());
            }
            currentProperties.putAll(actualMap);
            rateLimiter.acquire();
        }
    }
}
