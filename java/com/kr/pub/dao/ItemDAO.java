package com.kr.pub.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kr.pub.dto.ErpDTO;
import com.kr.pub.dto.ItemDTO;
import com.kr.pub.dto.OrderHistoryDTO;


@Mapper
public interface ItemDAO {

	
	//품목코드 가져오기
	public List<Map<String, Object>> statusCode() throws Exception;
	
	//재고 토탈 카운트
	public int itemTotalCount(ErpDTO erpDTO)throws Exception;
	
	//입출고 조회 조건
	public List<Map<String, Object>> statusSearch(Map<String, Object> params)throws Exception;

	//입출고 목록 출력
	public List<Map<String, Object>> statusList() throws Exception;
	
	//재고 등록
	public int insertStoreData(ItemDTO itemDTO) throws Exception;
	
	//재고 조회 조건 
	public List<Map<String, Object>> itemSearch(Map<String, Object> params)throws Exception;
	
	//재고 목록 출력
	public List<Map<String, Object>> itemList() throws Exception;
	
	//주문완료 후 수량 업데이트
	public void updateItemStock(OrderHistoryDTO orderHistory);
}