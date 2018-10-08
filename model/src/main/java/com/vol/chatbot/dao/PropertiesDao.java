package com.vol.chatbot.dao;

import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertiesDao extends JpaRepository<Property, Long> {

    Optional<Property> getByPropertyName(Properties propertyName);
}
