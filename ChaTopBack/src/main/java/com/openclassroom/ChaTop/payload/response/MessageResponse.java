package com.openclassroom.ChaTop.payload.response;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;



@Schema(description = "Requête pour créer un message")
@Data
public class MessageResponse {

  @Schema(description = "ID de l'utilisateur", example = "1", required = true)
  private Long user_id;

  @Schema(description = "ID de la location", example = "1", required = true)
  private Long rental_id;

  @Schema(description = "Contenu du message", example = "Bonjour, je suis intéressé par votre location.", required = true)
  private String message;

  public MessageResponse(String message, Long user_id, Long rental_id)
  {
    this.message = message;
    this.rental_id = rental_id;
    this.user_id = user_id;
  }
}
