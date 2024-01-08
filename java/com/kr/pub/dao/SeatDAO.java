package com.kr.pub.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeatDAO {

	public int getSeatNo(String ip);

}
