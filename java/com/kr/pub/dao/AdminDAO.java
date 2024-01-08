package com.kr.pub.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kr.pub.dto.UserDTO;

@Mapper
public interface AdminDAO {
	
	public List<UserDTO> getLoggedInUserList();

	public List<Map<String, Object>> getChartData();
	
	public List<Map<String, Object>> getPieChartData(String dateType);
	
	public Map<String, Object> getUserCount();

	public List<Map<String, Object>> getMonthlyUsers();
	
	public List<Map<String, Object>> getHourlyUsers(String type);

	public void shutDown();

	public List<Map<String, Object>> getChartData2();

	public void insertChartData();
	
	public void updateChartDataUser();

	public List<Map<String, Object>> getThisMonthData();
}
