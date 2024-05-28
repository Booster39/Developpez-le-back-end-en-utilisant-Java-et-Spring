package com.openclassroom.ChaTop.mapper;

import com.openclassroom.ChaTop.dto.MessageDto;
import com.openclassroom.ChaTop.models.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.openclassroom.ChaTop.models.Rental;
import com.openclassroom.ChaTop.models.User;
import com.openclassroom.ChaTop.repository.RentalRepository;
import com.openclassroom.ChaTop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class MessageMapper implements EntityMapper<MessageDto, Message> {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RentalRepository rentalRepository;

  @Override
  public Message toEntity(MessageDto dto) {
    if ( dto == null ) {
      return null;
    }

    Message.MessageBuilder message = Message.builder();

    message.message( dto.getMessage() );

    User user = userRepository.findById(dto.getUser_id()).orElse(null);
    message.user(user);

    Rental rental = rentalRepository.findById(dto.getRental_id()).orElse(null);
    message.rental(rental);

    message.id(dto.getId());
    message.created_at(dto.getCreated_at());
    message.updated_at(dto.getUpdated_at());

    return message.build();
  }

  @Override
  public MessageDto toDto(Message entity) {
    if ( entity == null ) {
      return null;
    }

    MessageDto messageDto = new MessageDto();


    messageDto.setMessage( entity.getMessage() );
    messageDto.setRental_id(entity.getRental().getId());
    messageDto.setUser_id(entity.getUser().getId());
    messageDto.setId(entity.getId());
    messageDto.setCreated_at(entity.getCreated_at());
    messageDto.setUpdated_at(entity.getUpdated_at());

    return messageDto;
  }

  @Override
  public List<Message> toEntity(List<MessageDto> dtoList) {
    if ( dtoList == null ) {
      return null;
    }

    return dtoList.stream()
      .map(this::toEntity)
      .collect(Collectors.toList());
  }

  @Override
  public List<MessageDto> toDto(List<Message> entityList) {
    if ( entityList == null ) {
      return null;
    }

    return entityList.stream()
      .map(this::toDto)
      .collect(Collectors.toList());
  }
}
