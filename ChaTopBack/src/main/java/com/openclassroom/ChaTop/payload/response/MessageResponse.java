package com.openclassroom.ChaTop.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MessageResponse {
  private String message;
  private Long user_id;
  private Long rental_id;

  public MessageResponse(String message) {this.message = message;}
}
