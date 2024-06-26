package com.openclassroom.ChaTop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Détails de la location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
  @Schema(description = "ID de la location", example = "1")
  private Long id;

  @Schema(description = "Nom de la location", example = "Appartement à Paris")
  @NotBlank
  @Size(max = 255)
  private String name;

  @Schema(description = "Surface de la location en mètres carrés", example = "45.0")
  private float surface;

  @Schema(description = "Prix de la location par mois", example = "1200.0")
  private float price;

  @Schema(description = "Chemin de l'image de la location", example = "/images/appartement_paris.jpg")
  @NonNull
  private String picture;

  @Schema(description = "Description de la location", example = "Appartement lumineux et spacieux à Paris")
  @NonNull
  @Size(max = 2000)
  private String description;

  @Schema(description = "ID de l'utilisateur", example = "1")
  private Long owner_id;

  @Schema(description = "Date de création de l'utilisateur", example = "01 January 2020")
  private LocalDateTime created_at;

  @Schema(description = "Date de modification de l'utilisateur", example = "02 January 2020")
  private LocalDateTime updated_at;

}
