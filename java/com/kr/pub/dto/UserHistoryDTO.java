package com.kr.pub.dto;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHistoryDTO {
	private String historyId;
	private String seatNo;
	private String userId;
	private Timestamp loginTime;
	private Timestamp logoutTime;
}
