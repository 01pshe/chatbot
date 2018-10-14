package com.vol.chatbot.web.controller;


import com.vol.chatbot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);
    private UserService userService;

    @Autowired
    public UserInfoController(UserService userInfoService) {
        this.userService = userInfoService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        LOGGER.debug("redirect to index.jsp");
        String message = "Hello Bot";

        model.addAttribute("message", message);

        return "index";
    }

    @RequestMapping("/users")
    public String listAll(Model model) {
        LOGGER.debug("redirect to user.jsp");
        model.addAttribute("users", userService.getAll());
        return "users";
    }
}
