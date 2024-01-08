package com.kr.pub.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kr.pub.dto.OrderDTO;
import com.kr.pub.dto.OrderHistoryDTO;
import com.kr.pub.dto.OrderListDTO;
import com.kr.pub.dto.UserDTO;

@Mapper
public interface OrderDAO {

	List<OrderListDTO> getOrderList();

	public List<Map<String, Object>> erpOrderSearch(Map<String, Object> params) throws Exception;
	
	public List<Map<String, Object>> erpOrderList() throws Exception;
	
	public List<Map<String, Object>> erpOrderView(Map<String, Object> params) throws Exception;

	public OrderDTO order(String userId);

	public void insertOrder(OrderDTO order);

	public void insertOrderHistory(OrderHistoryDTO orderHistory);

	int served(OrderDTO orderDTO);

	List<OrderListDTO> orderListById(OrderDTO orderDTO);

	public void insertChargeOrderHistory(List<OrderHistoryDTO> cartItems);
}
