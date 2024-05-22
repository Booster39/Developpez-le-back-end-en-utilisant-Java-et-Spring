package com.openclassroom.ChaTop.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private Long id;

  @NonNull
  @Size(max = 255)
  @Email
  private String email;

  @NonNull
  @Size(max = 255)
  private String name;

  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
