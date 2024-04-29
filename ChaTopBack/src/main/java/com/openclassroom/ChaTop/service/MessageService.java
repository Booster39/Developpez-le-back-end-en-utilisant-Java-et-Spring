package com.openclassroom.ChaTop.service;

import com.openclassroom.ChaTop.models.Message;
import com.openclassroom.ChaTop.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private final MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }
  public Message create(Message message) {
    return this.messageRepository.save(message);
  }

}
