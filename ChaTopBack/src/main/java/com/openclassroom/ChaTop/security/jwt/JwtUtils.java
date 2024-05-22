package com.openclassroom.ChaTop.security.jwt;

import com.openclassroom.ChaTop.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${oc.app.jwtSecret}")
  private String jwtSecret;

  @Value("${oc.app.jwtExpirationMs}")
  private Long jwtExpirationMs;

  private final Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);

  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
      .setSubject((userPrincipal.getUsername()))
      .setIssuedAt(new Date())
      .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
      .signWith(key)
      .compact();
  }
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
  }

  public String generateToken(String username) {
    return Jwts.builder()
      .setSubject(username)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
      .signWith(key)
      .compact();
  }

  public Claims validateAndExtractClaims(String jwtToken) {
    try {
      Jws<Claims> claimsJws = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(jwtToken);
      return claimsJws.getBody();
    } catch (Exception e) {
      return null;
    }
  }
}
