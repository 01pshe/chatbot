package com.vol.chatbot.services.propertiesservice;

import com.vol.chatbot.dao.PropertiesDao;
import com.vol.chatbot.model.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PropertiesServiceImpl implements PropertiesService, AutoCloseable {

    private Map<Properties, String> properties = new ConcurrentHashMap<>();

    private Thread refresherThread;

    private PropertiesDao propertiesDao;

    @Autowired
    public PropertiesServiceImpl(PropertiesDao propertiesDao) {
        this.propertiesDao = propertiesDao;
    }


    @Override
    public void close() throws Exception {
        refresherThread.interrupt();
    }

    public void setTps(double tps) {
        PropertiesRefresher refresher = new PropertiesRefresher(propertiesDao, properties);
        refresher.setTps(tps);
        refresherThread = new Thread(refresher);
        refresherThread.start();
    }

    @Override
    public Boolean getAsBoolean(Properties propertyName) {
        return Optional.ofNullable(properties.get(propertyName))
            .map(Boolean::valueOf)
            .orElse(Boolean.valueOf(propertyName.getDefaultVal()));
    }

    @Override
    public String getAsString(Properties propertyName) {
        return Optional.ofNullable(properties.get(propertyName))
            .orElse(propertyName.getDefaultVal());
    }

    @Override
    public Long getAsLong(Properties propertyName) {
        return Optional.ofNullable(properties.get(propertyName))
            .map(Long::valueOf)
            .orElse(Long.valueOf(propertyName.getDefaultVal()));
    }

    @Override
    public Integer getAsInteger(Properties propertyName) {
        return Optional.ofNullable(properties.get(propertyName))
            .map(Integer::valueOf)
            .orElse(Integer.valueOf(propertyName.getDefaultVal()));
    }

    @Override
    public Float getAsFloat(Properties propertyName) {
        return Optional.ofNullable(properties.get(propertyName))
            .map(Float::valueOf)
            .orElse(Float.valueOf(propertyName.getDefaultVal()));
    }

}
