package com.openclassroom.ChaTop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
  private Long id;

  @NotBlank
  @Size(max = 255)
  private String name;

  private Long ownerId;

  private BigDecimal surface;

  private BigDecimal price;

  @NonNull
  @Size(max = 255)
  private String picture;

  @NonNull
  @Size(max = 2000)
  private String description;
}
