package com.kr.pub.dto;

import lombok.Data;

@Data
public class OAuthToken {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String id_token;
	private long expires_in;
	private String scope;
	private long refresh_token_expires_in;
	
}
