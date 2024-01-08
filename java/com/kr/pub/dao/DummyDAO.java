package com.kr.pub.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kr.pub.dto.OrderDTO;
import com.kr.pub.dto.OrderHistoryDTO;
import com.kr.pub.dto.OrderListDTO;
import com.kr.pub.dto.PaymentDTO;
import com.kr.pub.dto.UserDTO;
import com.kr.pub.dto.UserHistoryDTO;

@Mapper
public interface DummyDAO {
	
	List<String> getUserIdList();
	
	void insertUserHistoryData(UserHistoryDTO history);
	
	void insertPayment(PaymentDTO payment);
	
	void insertOrderData(OrderDTO order);

	List<Map<String, Object>> getItemListData();
	
	
}
