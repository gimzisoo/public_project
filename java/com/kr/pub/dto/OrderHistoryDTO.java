package com.kr.pub.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryDTO {
	private String orderId;
	private List<ItemDTO> items; // item_id 컬럼
	private String itemId;
	private int quantity;
	private int price;
}
