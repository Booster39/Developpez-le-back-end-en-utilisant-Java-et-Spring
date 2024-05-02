package com.openclassroom.ChaTop.mapper;

import com.openclassroom.ChaTop.dto.UserDto;
import com.openclassroom.ChaTop.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper implements EntityMapper<UserDto, User> {

  @Override
  public User toEntity(UserDto dto) {
    if ( dto == null ) {
      return null;
    }

    User.UserBuilder user = User.builder();

    user.id( dto.getId() );
    user.name( dto.getName() );
    user.email( dto.getEmail() );
    user.messages(dto.getMessages());
    user.rentals(dto.getRentals());

    return user.build();
  }

  @Override
  public UserDto toDto(User entity) {
    if ( entity == null ) {
      return null;
    }

    UserDto userDto = new UserDto();

    userDto.setId( entity.getId() );
    userDto.setName( entity.getName() );
    userDto.setEmail( entity.getEmail() );
    userDto.setMessages(entity.getMessages());
    userDto.setRentals(entity.getRentals());

    return userDto;
  }

  @Override
  public List<User> toEntity(List<UserDto> dtoList) {
    if ( dtoList == null ) {
      return null;
    }

    List<User> list = new ArrayList<User>( dtoList.size() );
    for ( UserDto userDto : dtoList ) {
      list.add( toEntity( userDto ) );
    }

    return list;
  }

  @Override
  public List<UserDto> toDto(List<User> entityList) {
    if ( entityList == null ) {
      return null;
    }

    List<UserDto> list = new ArrayList<UserDto>( entityList.size() );
    for ( User user : entityList ) {
      list.add( toDto( user ) );
    }

    return list;
  }
}
