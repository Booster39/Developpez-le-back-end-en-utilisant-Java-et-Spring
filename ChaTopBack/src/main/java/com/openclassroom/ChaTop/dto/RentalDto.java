package com.openclassroom.ChaTop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
  private Long id;

  @NotBlank
  @Size(max = 255)
  private String name;

  private Long owner_id;

  private BigDecimal surface;

  private BigDecimal price;

  @NonNull
  @Size(max = 255)
  private String picture;

  @NonNull
  @Size(max = 2000)
  private String description;

  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
