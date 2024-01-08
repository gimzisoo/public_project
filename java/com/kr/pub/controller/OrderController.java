package com.kr.pub.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kr.pub.dto.OrderDTO;
import com.kr.pub.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@PostMapping("/served")
	@ResponseBody
	public Map<String, Object> served(@RequestBody OrderDTO orderDTO) {
		Map<String, Object> result =  new HashMap<>();
		result.put("result",orderService.served(orderDTO));
		return result;
	}
	@PostMapping("/listById")
	@ResponseBody
	public Map<String, Object> orderListById(@RequestBody OrderDTO orderDTO) {
		Map<String, Object> result =  new HashMap<>();
		result.put("result",orderService.orderListById(orderDTO));
		return result;
	}
}
