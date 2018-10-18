package com.vol.chatbot.web.controller;

import com.vol.chatbot.web.buttons.ButtonProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfigsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigsController.class);

    private ButtonProcessor buttonProcessor;
    private String status = "Бот запущен!";

    @Autowired
    public ConfigsController(ButtonProcessor buttonProcessor) {
        this.buttonProcessor = buttonProcessor;
    }

    @RequestMapping("/controls")
    public String index(Model model) {
        LOGGER.debug("redirect to controls.html");
        model.addAttribute("message", status);
        return "controls";
    }

    @PostMapping("/controls")
        public String controlBot(@RequestParam String action, Model model) {
        LOGGER.debug("Action is : {}", action);
            switch (action.toUpperCase()) {
                case "STOP" :
                        status = "Бот Остановлен!";
                    buttonProcessor.setSuspendMode();
                    break;
                case "START" :
                    status = "Бот Запущен!";
                    buttonProcessor.unsetSuspendMode();
                    break;
                case "OPENSECONDDAY" :
                    status = "Открыт второй день!";
                    buttonProcessor.startSecondDay();
                    break;
                    default: break;
            }
            model.addAttribute("message", status);
            return "controls";
        }
}
