package com.openclassroom.ChaTop.dto;

import com.openclassroom.ChaTop.models.Message;
import com.openclassroom.ChaTop.models.Rental;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

  @OneToMany(mappedBy = "user")
  private List<Message> messages;

  @OneToMany(mappedBy = "owner")
  private List<Rental> rentals;
}
