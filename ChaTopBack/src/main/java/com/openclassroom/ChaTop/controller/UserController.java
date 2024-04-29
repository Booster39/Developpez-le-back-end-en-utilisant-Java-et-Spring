package com.openclassroom.ChaTop.controller;

import com.openclassroom.ChaTop.dto.UserDto;
import com.openclassroom.ChaTop.mapper.UserMapper;
import com.openclassroom.ChaTop.models.User;
import com.openclassroom.ChaTop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
  private final UserService userService;
  private final UserMapper userMapper;

  public UserController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> findById(@PathVariable("id") String id) {
    try {
     User user = this.userService.findById(Long.valueOf(id));

      if (user == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok().body(this.userMapper.toDto(user));
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
