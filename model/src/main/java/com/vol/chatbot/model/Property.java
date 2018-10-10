package com.vol.chatbot.model;

import javax.persistence.*;

@Entity
@Table(name = "bot_properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_name", unique = true, nullable = false)
    private Properties propertyName;

    @Column(name = "property_value")
    private String propertyValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Properties getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(Properties propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
