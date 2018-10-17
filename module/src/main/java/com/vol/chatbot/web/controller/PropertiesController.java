package com.vol.chatbot.web.controller;

import com.vol.chatbot.dao.PropertiesDao;
import com.vol.chatbot.web.properties.PropWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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


    @RequestMapping(value={"/properties"},method = RequestMethod.POST)
    public String save(PropWrapper wrapper, Model model) {
        LOGGER.info("msg ",wrapper);
        propertiesDao.saveAll(wrapper.getProps());
        model.addAttribute("wrapper",wrapper);
        return "properties";
    }


}
