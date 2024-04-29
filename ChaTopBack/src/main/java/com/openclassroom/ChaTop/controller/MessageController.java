package com.openclassroom.ChaTop.controller;

import com.openclassroom.ChaTop.dto.MessageDto;
import com.openclassroom.ChaTop.mapper.MessageMapper;
import com.openclassroom.ChaTop.models.Message;
import com.openclassroom.ChaTop.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/messages")
@Log4j2
public class MessageController {
  private final MessageService messageService;
  private final MessageMapper messageMapper;

  public MessageController(MessageService messageService, MessageMapper messageMapper) {
    this.messageService = messageService;
    this.messageMapper = messageMapper;
  }

  @PostMapping()
  public ResponseEntity<?> create(@Valid @RequestBody MessageDto messageDto) {
    log.info(messageDto);

    Message message = this.messageService.create(this.messageMapper.toEntity(messageDto));

    log.info(message);
    return ResponseEntity.ok().body(this.messageMapper.toDto(message));
  }
}
