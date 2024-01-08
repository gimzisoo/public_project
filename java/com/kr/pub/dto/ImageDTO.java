package com.kr.pub.dto;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
	private String imgId;
	private Date regDate;
	private long length;
	private String imgNameReal;
	private char deleted;
	private String contentType;
	private String imgNameOrg;
	
	private String itemId;
		
}
