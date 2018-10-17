package com.vol.chatbot.web.controller;

import com.vol.chatbot.dao.PropertiesDao;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.Property;
import com.vol.chatbot.web.properties.PropWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PropertiesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesController.class);

    private PropertiesDao propertiesDao;

    @Autowired
    public PropertiesController(PropertiesDao propertiesDao) {
        this.propertiesDao = propertiesDao;
    }

    @RequestMapping(value={"/properties"},method = RequestMethod.GET)
    public String listAll(Model model) {
        PropWrapper propWrapper = new PropWrapper();
        propWrapper.setProps(propertiesDao.findAll());
        model.addAttribute("wrapper",propWrapper);
        return "properties";
    }

    @RequestMapping(value={"/properties/{id}"},method = RequestMethod.GET)
    public Property getOne(@PathVariable("id") long id, Model model) {
        Property prop = propertiesDao.findById(id).orElse(null);
        return prop;
    }

    @RequestMapping(value={"/properties"},method = RequestMethod.POST)
    public String saveOne(Model model) {
                model.addAttribute("properties",propertiesDao.findAll());
        return "properties";
    }


}
