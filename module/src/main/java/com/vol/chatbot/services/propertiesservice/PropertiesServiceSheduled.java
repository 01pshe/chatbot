package com.vol.chatbot.services.propertiesservice;

import com.vol.chatbot.dao.PropertiesDao;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PropertiesServiceSheduled implements PropertiesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesServiceSheduled.class);

    private Map<Properties, String> properties = new ConcurrentHashMap<>();
    private final Object lockObj = new Object();


    private PropertiesDao propertiesDao;
    private long propertiesRefreshTime;


    @Autowired
    public PropertiesServiceSheduled(PropertiesDao propertiesDao) {
        this.propertiesDao = propertiesDao;
        this.propertiesRefreshTime = this.getAsInteger(Properties.REFRESH_PROPERTIES_TIME);
    }

    @PostConstruct
    private void startScheduler() {
        refreshProperties();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::refreshProperties, 1, this.propertiesRefreshTime, TimeUnit.SECONDS);
    }

    @Override
    public void refreshProperties() {
        try {
            synchronized (lockObj) {
                List<Property> actualProperties = propertiesDao.findAll();
                Map<Properties, String> actualMap = new EnumMap<>(Properties.class);
                for (Property prop : actualProperties) {
                    actualMap.put(prop.getPropertyName(), prop.getPropertyValue());
                }
                properties.putAll(actualMap);
                LOGGER.info("Обновили список настроек.");
            }
        } catch (Exception e) {
            LOGGER.info("Ошибка обновления списока настроек.", e);
        }
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
