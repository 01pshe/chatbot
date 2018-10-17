package com.vol.chatbot.web.properties;

import com.vol.chatbot.model.Property;

import java.util.ArrayList;
import java.util.List;

public class PropWrapper {

    private List<Property> props = new ArrayList<>(0);

    public List<Property> getProps() {
        return props;
    }

    public void setProps(List<Property> props) {
        this.props = props;
    }
}
