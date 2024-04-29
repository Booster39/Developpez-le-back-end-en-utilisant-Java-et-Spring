package com.openclassroom.ChaTop.service;

import com.openclassroom.ChaTop.repository.UserRepository;
import com.openclassroom.ChaTop.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  public UserService(UserRepository userRepository) {this.userRepository = userRepository;}
  public User findById(Long id) {
    return this.userRepository.findById(id).orElse(null);
  }

}
