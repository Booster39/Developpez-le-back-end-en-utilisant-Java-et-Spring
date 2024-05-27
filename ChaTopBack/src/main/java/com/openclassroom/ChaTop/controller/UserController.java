package com.openclassroom.ChaTop.controller;

import com.openclassroom.ChaTop.dto.UserDto;
import com.openclassroom.ChaTop.mapper.UserMapper;
import com.openclassroom.ChaTop.models.User;
import com.openclassroom.ChaTop.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired
  private UserService userService;
  @Autowired
  private UserMapper userMapper;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDto> findById(@PathVariable("id") String id) {
    try {
     User user = this.userService.findById(Long.valueOf(id));

      if (user == null) {
        return ResponseEntity.notFound().build();
      }
    /*  return ResponseEntity.ok().body(this.userMapper.toDto(user));*/
      var dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);

      // Format the LocalDate to a string
      String formattedDateString = user.getCreated_at().format(dateTimeFormatter);

      var userDto = User.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .created_at(user.getCreated_at())
        .build();
      return ResponseEntity.ok().body(this.userMapper.toDto(userDto));
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
