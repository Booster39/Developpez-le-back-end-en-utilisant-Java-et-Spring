package com.openclassroom.ChaTop.payload.response;

import lombok.Data;


@Data
public class MessageResponse {
  private String message;
  private Long user_id;
  private Long rental_id;

  public MessageResponse(String message, Long user_id, Long rental_id)
  {
    this.message = message;
    this.rental_id = rental_id;
    this.user_id = user_id;
  }
}
