package com.kr.pub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErpDTO {
	
	private String startDate;
	private String endDate;
	private String name;
	private String select;
	private int stock;	
	private String code;
	private String status;
	
	private String paymentId;
	private String orderId;
	private String uname;

	//페이징
	private int pageNo = 1;		   // 현재 페이지 번호
	private int totalCount; 	  //전체 건수
	private int totalPageSize; 	  //전체 페이지수
	private int pageLength = 20; //한페이지의 길이
	private int navSize = 10;	 //페이지 하단에 출력되는 페이지의 항목수
	private int navStart = 0;    //페이지 하단에 출력되는 페이지 시작 번호 : NavStart = (PageNo / NavSize) * NavSize + 1
	private int navEnd = 0;     //페이지 하단에 출력되는 페이지 끝 번호 : NavEnd = (PageNo / NavSize + 1) * NavSize
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		//전체 페이지 건수를 계산
		totalPageSize = (int) Math.ceil((double) totalCount / pageLength);
		
		// 페이지 네비게이터 시작 페이지를 계산
		navStart = ((pageNo -1) / navSize) * navSize + 1;
		
		//페이지 네비게이터 끝 페이지를 계산
		navEnd = ((pageNo -1) / navSize + 1) * navSize;
		
		//전체 페이지 보다 크면 전체 페이지 값을 변경
		if(navEnd >= totalPageSize) {
			navEnd = totalPageSize;
		}
	}
	
	public int getStartNo() {
		return (pageNo -1) * pageLength + 1;
	}
	
	public int getEndNo() {
		return pageNo * pageLength;
	}
	
}
