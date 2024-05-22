package com.openclassroom.ChaTop.dto;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import com.openclassroom.ChaTop.models.Rental;
import com.openclassroom.ChaTop.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class MessageDto {

  @NonNull
  @Size(max = 2000)
  private String message;
  private Long user_id;
  private Long rental_id;


  public MessageDto(String message, Long user_id, Long rental_id) {
    this.message = message;
    this.user_id = user_id;
    this.rental_id = rental_id;
  }

}
