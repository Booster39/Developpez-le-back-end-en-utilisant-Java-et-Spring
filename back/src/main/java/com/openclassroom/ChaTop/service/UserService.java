package com.openclassroom.ChaTop.service;

import com.openclassroom.ChaTop.dto.UserDto;
import com.openclassroom.ChaTop.mapper.UserMapper;
import com.openclassroom.ChaTop.repository.UserRepository;
import com.openclassroom.ChaTop.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public User findById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  public UserDto findUserDtoById(Long id) {
    User user = findById(id);
    if (user == null) {
      return null;
    }
    return userMapper.toDto(user);
  }
}
