package com.kr.pub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kr.pub.dao.PaymentDAO;
import com.kr.pub.dto.PaymentDTO;

@Service
public class PaymentService {
	@Autowired
	private PaymentDAO paymentDAO;

	public boolean insertPayment(PaymentDTO paymentDTO) {
		return paymentDAO.insert(paymentDTO) != 0;
	}
}
