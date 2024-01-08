package com.kr.pub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PubSubMessageDTO {
	private String cacheName;
	private String grp_cd;
	private String cd;
	
	public static PubSubMessageDTO of(String cacheName, String grp_cd, String cd) {
		return new PubSubMessageDTO(cacheName, grp_cd, cd);
	}
	
}
