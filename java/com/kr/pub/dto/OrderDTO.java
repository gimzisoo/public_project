package com.kr.pub.dto;

import java.util.ArrayList;
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
public class OrderDTO {
	private String orderId;
	private Date orderDate;
	private String userId;
	private String code;
	private String remarks;
	private String served;
	
	private int remainingTime;
	
	
	private List<OrderHistoryDTO> items;
	private PaymentDTO paymentMethodCode;
	
}
