package com.vol.chatbot.web.controller;


import com.vol.chatbot.messagesender.MessageSender;
import com.vol.chatbot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);
    private UserService userService;
    private MessageSender messageSender;

    @Autowired
    public UserInfoController(UserService userInfoService, MessageSender messageSender) {
        this.userService = userInfoService;
        this.messageSender = messageSender;
    }

    @GetMapping({"/", "/users"})
    public String listAll(Model model) {
        LOGGER.debug("redirect to user.html");
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping("/messages")
    public String messages() {
        LOGGER.info("Redirect to message.html");
        return "messages";
    }

    @PostMapping("/messages")
    public String sendMessage(@RequestParam String text) {
        LOGGER.info("Send message to All users '{}'", text);
        messageSender.sendAll(text);
        return "messages";
    }
}
