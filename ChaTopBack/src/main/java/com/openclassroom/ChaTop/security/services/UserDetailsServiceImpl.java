package com.openclassroom.ChaTop.security.services;

import com.openclassroom.ChaTop.models.User;
import com.openclassroom.ChaTop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
    User user = userRepository.findById(id)
      .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

    return UserDetailsImpl
      .builder()
      .id(user.getId())
      .username(user.getEmail())
      .email(user.getEmail())
      .name(user.getName())
      .password(user.getPassword())
      .build();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return null;
  }
}
