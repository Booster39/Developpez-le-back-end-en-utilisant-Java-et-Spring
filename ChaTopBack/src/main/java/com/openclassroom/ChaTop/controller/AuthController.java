package com.openclassroom.ChaTop.controller;

import com.openclassroom.ChaTop.service.JWTService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
  private JWTService jwtService;
  public AuthController(JWTService jwtService) {this.jwtService = jwtService;}

  @PostMapping("/login")
  public String getToken(Authentication authentication) {
    String token = jwtService.generateToken(authentication);
    return token;
  }
}
