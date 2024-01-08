package com.kr.pub.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreHistoryDTO {
	private String storeId;
	private List<ItemDTO> items; //  item_id 컬럼
	private int quantity;
	private int price;
	
}
