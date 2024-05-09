package com.openclassroom.ChaTop.controller;

import com.openclassroom.ChaTop.dto.MessageDto;
import com.openclassroom.ChaTop.dto.RentalDto;
import com.openclassroom.ChaTop.mapper.MessageMapper;
import com.openclassroom.ChaTop.models.Message;
import com.openclassroom.ChaTop.payload.response.MessageResponse;
import com.openclassroom.ChaTop.service.MessageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.log4j.Log4j2;

import javax.validation.Valid;
import java.util.HashMap;

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
  public ResponseEntity<HashMap< MessageResponse ,MessageDto>> create(@Valid @RequestBody MessageDto messageDto) {
    log.info(messageDto);

    Message message = this.messageService.create(this.messageMapper.toEntity(messageDto));
    var response = new HashMap<
      MessageResponse, MessageDto>();
    response.put(new MessageResponse("Message send with success"), this.messageMapper.toDto(message) );

    log.info(message);
    return ResponseEntity.ok().body(response);
  }
}
