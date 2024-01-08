package com.kr.pub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
  private String type;
  private String sender;
  private String receiver;
  private String message;
  private int seatNo;
  private String time;
}
