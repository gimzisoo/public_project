package com.kr.pub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kr.pub.service.PaymentService;

@Controller
public class PaymentController {
	@Autowired
	PaymentService paymentService;
	
}
