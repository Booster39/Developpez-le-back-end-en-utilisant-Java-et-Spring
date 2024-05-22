package com.openclassroom.ChaTop.controller;

import com.openclassroom.ChaTop.dto.UserDto;
import com.openclassroom.ChaTop.mapper.UserMapper;
import com.openclassroom.ChaTop.models.User;
import com.openclassroom.ChaTop.payload.request.LoginRequest;
import com.openclassroom.ChaTop.payload.request.SignupRequest;
import com.openclassroom.ChaTop.payload.response.JwtResponse;
import com.openclassroom.ChaTop.payload.response.MessageResponse;
import com.openclassroom.ChaTop.repository.UserRepository;
import com.openclassroom.ChaTop.security.jwt.JwtUtils;
import com.openclassroom.ChaTop.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    AuthController(AuthenticationManager authenticationManager,
                   PasswordEncoder passwordEncoder,
                   JwtUtils jwtUtils,
                   UserRepository userRepository, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = this.userRepository.findByEmail(userDetails.getEmail()).orElse(null);
        if (user == null) {
          return ResponseEntity.badRequest().body(new MessageResponse("error" ));
        }
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
      if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        return ResponseEntity
          .badRequest().body("{}");
      }
      // Create new user's account
        User user = new User(signUpRequest.getEmail(),
                signUpRequest.getName(),
                passwordEncoder.encode(signUpRequest.getPassword())
                );
        userRepository.save(user);
        return ResponseEntity.ok(new JwtResponse(jwtUtils.generateToken(user.getEmail())));
    }

  @GetMapping("/me")
  public ResponseEntity<Optional<UserDto>> me() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
   final Optional<User> user = this.userRepository.findByEmail(auth.getName());
   return  ResponseEntity.ok().body(user.map(this.userMapper::toDto));
  }
}
