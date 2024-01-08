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
public class StoreDTO {
	private String storeId;
	private Date storeDate;
	private String empId;
	private String code;
	private String remarks;
}
