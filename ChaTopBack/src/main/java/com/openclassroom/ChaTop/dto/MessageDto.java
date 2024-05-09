package com.openclassroom.ChaTop.dto;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

  private LocalDateTime created_at;
  private LocalDateTime updated_at;

}
