package com.kr.pub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kr.pub.dao.OrderDAO;
import com.kr.pub.dto.OrderDTO;
import com.kr.pub.dto.OrderListDTO;

@Service
public class OrderService {
	@Autowired
	OrderDAO orderDAO;

	public boolean served(OrderDTO orderDTO) {
		return 0 != orderDAO.served(orderDTO);
	}

	public List<OrderListDTO> orderListById(OrderDTO orderDTO) {
		return orderDAO.orderListById(orderDTO);
	}
}
