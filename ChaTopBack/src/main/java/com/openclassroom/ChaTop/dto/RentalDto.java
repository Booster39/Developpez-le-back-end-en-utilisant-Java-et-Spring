package com.openclassroom.ChaTop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


import com.openclassroom.ChaTop.models.Rental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


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

  private String jwt;

  private LocalDateTime created_at;
  private LocalDateTime updated_at;

}
