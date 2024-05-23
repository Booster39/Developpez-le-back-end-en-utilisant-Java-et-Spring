package com.openclassroom.ChaTop.security.jwt;

import com.openclassroom.ChaTop.security.services.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      final String authorization = request.getHeader("Authorization");

      if (authorization != null && authorization.startsWith("Bearer ")) {
        final String token = authorization.substring(7); // Remove "Bearer ";

        // Ensure parseJwt method returns the complete JWT token string
        final String jwtToken = parseJwt(request);


        // Validate and extract claims from the complete JWT token string
        final Claims claims = jwtUtils.validateAndExtractClaims(jwtToken);

        if (claims != null && claims.getExpiration().after(new Date())) {
          final String username = claims.getSubject();
          System.out.println("Username :" + username);
          final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

          final UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());

          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }

    return null;
  }
}
