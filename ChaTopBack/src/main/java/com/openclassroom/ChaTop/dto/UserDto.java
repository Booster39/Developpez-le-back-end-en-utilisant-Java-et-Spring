package com.openclassroom.ChaTop.dto;

import com.openclassroom.ChaTop.models.Message;
import com.openclassroom.ChaTop.models.Rental;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

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

  private List<Long> messages;

  private List<Long> rentals;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
