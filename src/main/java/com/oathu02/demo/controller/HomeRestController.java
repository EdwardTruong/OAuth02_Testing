package com.oathu02.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {

  @GetMapping("/user")
  public ResponseEntity<OAuth2User> user(@AuthenticationPrincipal OAuth2User principal) {

    return new ResponseEntity<>(principal, HttpStatus.OK);
  }
}