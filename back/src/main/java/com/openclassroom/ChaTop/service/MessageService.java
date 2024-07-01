package com.openclassroom.ChaTop.service;

import com.openclassroom.ChaTop.dto.MessageDto;
import com.openclassroom.ChaTop.mapper.MessageMapper;
import com.openclassroom.ChaTop.models.Message;
import com.openclassroom.ChaTop.models.Rental;
import com.openclassroom.ChaTop.models.User;
import com.openclassroom.ChaTop.payload.response.MessageResponse;
import com.openclassroom.ChaTop.repository.MessageRepository;
import com.openclassroom.ChaTop.repository.RentalRepository;
import com.openclassroom.ChaTop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RentalRepository rentalRepository;
  @Autowired
  private MessageMapper messageMapper;


  public MessageDto createMessage(MessageResponse messageResponse) {
    User user = userRepository.findById(messageResponse.getUser_id())
            .orElseThrow(() -> new RuntimeException("User not found"));
    Rental rental = rentalRepository.findById(messageResponse.getRental_id())
            .orElseThrow(() -> new RuntimeException("Rental not found"));

    Message message = Message.builder()
            .message(messageResponse.getMessage())
            .user(user)
            .rental(rental)
            .build();

    Message savedMessage = messageRepository.save(message);
    return messageMapper.toDto(savedMessage);
  }
}

