package com.openclassroom.ChaTop.mapper;

import com.openclassroom.ChaTop.dto.UserDto;
import com.openclassroom.ChaTop.models.Message;
import com.openclassroom.ChaTop.models.Rental;
import com.openclassroom.ChaTop.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    user.created_at(dto.getCreated_at());
    user.updated_at(dto.getUpdated_at());

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
    userDto.setCreated_at(entity.getCreated_at());
    userDto.setUpdated_at(entity.getUpdated_at());


    return userDto;
  }


  @Override
  public List<User> toEntity(List<UserDto> dtoList) {
    if (dtoList == null) {
      return null;
    }

    return dtoList.stream()
      .map(this::toEntity)
      .collect(Collectors.toList());
  }

  @Override
  public List<UserDto> toDto(List<User> entityList) {
    if (entityList == null) {
      return null;
    }

    return entityList.stream()
      .map(this::toDto)
      .collect(Collectors.toList());
  }
}
