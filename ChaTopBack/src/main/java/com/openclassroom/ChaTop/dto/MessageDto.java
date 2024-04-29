package com.openclassroom.ChaTop.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
  private Long id;

  @NonNull
  @Size(max = 2000)
  private String message;

  private Long rental_id;

  private Long user_id;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
