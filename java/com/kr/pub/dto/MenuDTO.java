package com.kr.pub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
	private String itemId;
	private String imgId;
	private String menuCategoryCode;
	private String menuChecked;
	
	
	private String menuCategory; // common_code 테이블과 조인한 (menuCategoryCode)
	private ItemDTO item; // item 테이블과 조인 (itemId)
}
