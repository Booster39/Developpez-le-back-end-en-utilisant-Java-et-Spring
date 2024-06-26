package com.openclassroom.ChaTop.dto;

import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
  @Schema(description = "ID du message", example = "1")
  private Long id;

  @Schema(description = "Contenu du message", example = "Appartement lumineux et spacieux à Paris")
  @NonNull
  @Size(max = 2000)
  private String message;

  @Schema(description = "ID de l'utilisateur", example = "1")
  private Long user_id;

  @Schema(description = "ID de la location", example = "1")
  private Long rental_id;

  @Schema(description = "Date de création de l'utilisateur", example = "01 January 2020")
  private LocalDateTime created_at;

  @Schema(description = "Date de modification de l'utilisateur", example = "02 January 2020")
  private LocalDateTime updated_at;

}
