package com.example.oauth02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  @GetMapping("/info")
  public String userInfo() {
    return "userInfo";
  }

}
