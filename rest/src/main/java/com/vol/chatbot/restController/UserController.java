package com.vol.chatbot.restController;

import com.vol.chatbot.restService.RestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

  private RestUserService restUserService;

  @Autowired
  public UserController(RestUserService restUserService) {
    this.restUserService = restUserService;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Model model) {
    String message = "Hello ChatBot";

    model.addAttribute("message", message);
    return "message";
  }

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public String listAll(Model model) {
    model.addAttribute("users", restUserService.getAll());
    return "users";
  }

}
