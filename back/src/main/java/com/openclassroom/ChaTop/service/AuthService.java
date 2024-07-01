package com.openclassroom.ChaTop.service;

import com.openclassroom.ChaTop.dto.UserDto;
import com.openclassroom.ChaTop.mapper.UserMapper;
import com.openclassroom.ChaTop.models.User;
import com.openclassroom.ChaTop.payload.request.LoginRequest;
import com.openclassroom.ChaTop.payload.request.SignupRequest;
import com.openclassroom.ChaTop.payload.response.JwtResponse;
import com.openclassroom.ChaTop.repository.UserRepository;
import com.openclassroom.ChaTop.security.jwt.JwtUtils;
import com.openclassroom.ChaTop.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public JwtResponse authenticateUser(LoginRequest loginRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = this.userRepository.findByEmail(userDetails.getUsername()).orElse(null);

        if (user == null) {
            throw new Exception("User not found");
        }

        return new JwtResponse(jwt);
    }

    public JwtResponse registerUser(SignupRequest signUpRequest) throws Exception {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new Exception("User already exists");
        }

        // Create new user's account
        User user = new User(
                signUpRequest.getEmail(),
                signUpRequest.getName(),
                passwordEncoder.encode(signUpRequest.getPassword())
        );

        userRepository.save(user);
        return new JwtResponse(jwtUtils.generateJwtToken(user.getEmail()));
    }

    public Optional<UserDto> getCurrentUser(Authentication auth) {
        final Optional<User> user = this.userRepository.findByEmail(auth.getName());
        return user.map(this.userMapper::toDto);
    }
}
