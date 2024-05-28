package com.openclassroom.ChaTop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
  private Long id;

  @NotBlank
  @Size(max = 255)
  private String name;

  private float surface;

  private float price;

  @NonNull
  @Size(max = 255)
  private String picture;

  @NonNull
  @Size(max = 2000)
  private String description;

  private Long owner_id;


  private LocalDateTime created_at;
  private LocalDateTime updated_at;

}
