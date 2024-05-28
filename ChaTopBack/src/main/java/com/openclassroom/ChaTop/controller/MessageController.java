package com.openclassroom.ChaTop.controller;

import com.openclassroom.ChaTop.mapper.MessageMapper;
import com.openclassroom.ChaTop.models.Message;

import com.openclassroom.ChaTop.payload.response.MessageResponse;
import com.openclassroom.ChaTop.payload.response.StringResponse;
import com.openclassroom.ChaTop.repository.MessageRepository;
import com.openclassroom.ChaTop.repository.RentalRepository;
import com.openclassroom.ChaTop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.log4j.Log4j2;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/messages")
@Log4j2
public class MessageController {
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RentalRepository rentalRepository;

  @Autowired
  private MessageMapper messageMapper;



  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@Valid @RequestBody MessageResponse messageResponse) {
    try {
    var users = userRepository.findById(messageResponse.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));
    var rentals = rentalRepository.findById(messageResponse.getRental_id()).orElseThrow(() -> new RuntimeException("Rental not found"));
    var messages = Message.builder()
      .message(messageResponse.getMessage())
      .user(users)
      .rental(rentals)
      .build();
    messageRepository.save(messages);
    return new ResponseEntity<StringResponse>(new StringResponse("Message sent with success"), HttpStatus.CREATED);
    } catch (Exception e)
    {
      return ResponseEntity.badRequest().build();
    }
  }

}
