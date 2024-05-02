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
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

    return UserDetailsImpl
      .builder()
      .id(user.getId())
      .password(user.getPassword())
      .name(user.getName())
      .email(user.getEmail())
      .username(user.getEmail())
      .build();
  }
}
