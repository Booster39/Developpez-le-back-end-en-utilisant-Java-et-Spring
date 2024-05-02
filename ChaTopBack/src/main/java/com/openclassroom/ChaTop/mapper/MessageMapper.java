package com.openclassroom.ChaTop.mapper;

import com.openclassroom.ChaTop.dto.MessageDto;
import com.openclassroom.ChaTop.models.Message;
import java.util.ArrayList;
import java.util.List;

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

    message.id( dto.getId() );
    message.message( dto.getMessage() );

    User user = userRepository.findById(dto.getUserId()).orElse(null);
    message.user(user);

    Rental rental = rentalRepository.findById(dto.getRentalId()).orElse(null);
    message.rental(rental);

    return message.build();
  }

  @Override
  public MessageDto toDto(Message entity) {
    if ( entity == null ) {
      return null;
    }

    MessageDto messageDto = new MessageDto();

    messageDto.setId( entity.getId() );
    messageDto.setMessage( entity.getMessage() );
    messageDto.setRentalId(entity.getRental().getId());
    messageDto.setUserId(entity.getUser().getId());

    return messageDto;
  }

  @Override
  public List<Message> toEntity(List<MessageDto> dtoList) {
    if ( dtoList == null ) {
      return null;
    }

    List<Message> list = new ArrayList<Message>( dtoList.size() );
    for ( MessageDto messageDto : dtoList ) {
      list.add( toEntity( messageDto ) );
    }

    return list;
  }

  @Override
  public List<MessageDto> toDto(List<Message> entityList) {
    if ( entityList == null ) {
      return null;
    }

    List<MessageDto> list = new ArrayList<MessageDto>( entityList.size() );
    for ( Message message : entityList ) {
      list.add( toDto( message ) );
    }

    return list;
  }
}
