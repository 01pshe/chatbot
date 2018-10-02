package com.vol.chatbot.restController;

import com.vol.chatbot.model.UserInfo;
import com.vol.chatbot.restService.RestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

//  private List<Map<String, String>> userSevice = new ArrayList<Map<String, String>>(){{
//    add(new HashMap<String, String>(){{put("id", "1"); put("FirstName","Ivanov");}});
//  }};

  private RestUserService restUserService;

  @Autowired
  public UserController(RestUserService restUserService) {
    this.restUserService = restUserService;
  }


  @GetMapping

  public List<UserInfo> listAll() {
    List<UserInfo> all = restUserService.getAll();
    return all;
  }

}
