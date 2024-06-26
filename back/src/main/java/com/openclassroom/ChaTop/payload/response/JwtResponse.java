package com.openclassroom.ChaTop.payload.response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "Réponse JWT")
public class JwtResponse {
  @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String token;

  public JwtResponse(String accessToken) {
    this.token = accessToken;
  }
}
