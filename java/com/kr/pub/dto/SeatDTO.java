package com.kr.pub.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {
	private String seatNo;
	private char userd;
	private char damaged;
	private int ipAddress;
}
