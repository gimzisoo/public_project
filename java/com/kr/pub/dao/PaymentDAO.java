package com.kr.pub.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kr.pub.dto.ErpDTO;
import com.kr.pub.dto.PaymentDTO;

@Mapper
public interface PaymentDAO {

	//매출 토탈 카운트
	public int paymentTotalCount(ErpDTO erp)throws Exception;
	
	//매출 조회
	public List<Map<String, Object>> salesSearch(Map<String, Object> params) throws Exception;
	
	//매출 내역
	public List<Map<String, Object>> salesList()throws Exception;

//	public void insertPayment(OrderDTO order);
	public int insert(PaymentDTO paymentDTO);

}