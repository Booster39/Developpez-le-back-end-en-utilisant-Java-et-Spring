package com.openclassroom.ChaTop.configuration;

import com.openclassroom.ChaTop.security.jwt.AuthEntryPointJwt;
import com.openclassroom.ChaTop.security.jwt.AuthTokenFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthEntryPointJwt authEntryPointJwt;


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

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(cors -> cors.configurationSource(request -> {
                  var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                  corsConfiguration.setAllowedOrigins(List.of( "*"));
                  corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "*"));
                  corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "*"));
                  corsConfiguration.setAllowCredentials(true);
                  return corsConfiguration;
            }))
    .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(authenticationProvider())

      .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class
      );
    http
      .authorizeHttpRequests(auth -> auth
        .antMatchers("/auth/welcome", "/auth/register", "/auth/login").permitAll()
        .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
        .antMatchers("/api/auth/**").permitAll()
        .antMatchers("/public/**", "/uploads/**", "/images/**").permitAll()
        .anyRequest().authenticated()
      )
      .exceptionHandling(exception -> exception
        .authenticationEntryPoint(authEntryPointJwt));

    return http.build();
  }

  private AuthenticationProvider authenticationProvider() {
    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }


}
