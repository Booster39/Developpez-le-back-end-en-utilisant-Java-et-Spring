package com.openclassroom.ChaTop.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Détails de l'utilisateur")
public class UserDto {

  @Schema(description = "ID de l'utilisateur", example = "1")
  private Long id;

  @Schema(description = "Nom de l'utilisateur", example = "John Doe")
  @NonNull
  @Size(max = 255)
  private String name;

  @Schema(description = "Email de l'utilisateur", example = "john.doe@example.com")
  @Email
  @NonNull
  @Size(max = 255)
  private String email;

  @Schema(description = "Date de création de l'utilisateur", example = "01 January 2020")
  private LocalDateTime created_at;

  @Schema(description = "Date de modification de l'utilisateur", example = "02 January 2020")
  private LocalDateTime updated_at;
}
