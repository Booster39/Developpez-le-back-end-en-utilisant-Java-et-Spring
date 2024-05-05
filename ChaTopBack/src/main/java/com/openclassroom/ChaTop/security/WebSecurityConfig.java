package com.openclassroom.ChaTop.security;

import com.openclassroom.ChaTop.security.jwt.AuthEntryPointJwt;
import com.openclassroom.ChaTop.security.jwt.AuthTokenFilter;
import com.openclassroom.ChaTop.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

 private final UserDetailsServiceImpl userDetailsServiceImplem;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  AuthEntryPointJwt unauthorizedHandler;

  @Autowired
  public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImplem) {
    this.userDetailsServiceImplem = userDetailsServiceImplem;
  }


  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

/*  @Bean
  public UserDetailsService userDetailsService() throws Exception {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User
      .withUsername("test@test.com")
      .password(passwordEncoder().encode("test!31"))
      .build());
    return manager;
  }*/

  @Bean
  public UserDetailsService users() throws Exception {
    UserDetails user = User.builder()
      .username("test@test.com")
      .password(passwordEncoder().encode("test!31"))
      .build();
    return new InMemoryUserDetailsManager(user);
  }
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     //formLogin(Customizer.withDefaults());
    http
   //.cors(AbstractHttpConfigurer::disable)
      .csrf(AbstractHttpConfigurer::disable)
     // .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(unauthorizedHandler))
    .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(authenticationProvider())
      .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class
      );
    http
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/auth/welcome", "/auth/register", "/auth/login").permitAll()
        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
        .requestMatchers("/api/auth/**").permitAll()
        .requestMatchers("/api/**").authenticated()
        .anyRequest().authenticated());
      //.httpBasic(withDefaults());

    return http.build();
  }

  private AuthenticationProvider authenticationProvider() {
    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

}
/*
*  .requestMatchers("/auth/welcome", "/auth/register", "/auth/login").permitAll()
      .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
     */
