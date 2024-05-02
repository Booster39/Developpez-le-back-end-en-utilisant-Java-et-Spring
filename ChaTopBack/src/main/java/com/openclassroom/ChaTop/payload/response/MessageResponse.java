package com.openclassroom.ChaTop.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MessageResponse {
  private String message;

  public MessageResponse(String message) {
    this.message = message;
  }
}
