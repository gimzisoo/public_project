package com.kr.pub.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kr.pub.dao.AdminDAO;
import com.kr.pub.dao.ImageDAO;
import com.kr.pub.dao.MenuDAO;
import com.kr.pub.dao.OrderDAO;
import com.kr.pub.dto.MenuDTO;
import com.kr.pub.dto.OrderListDTO;
import com.kr.pub.dto.UserDTO;

@Service
public class AdminService {
	@Autowired
	CacheManager cacheManager;
	
	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private MenuDAO menuDAO;

	@Autowired
	private ImageDAO imageDAO;
	

	public List<Map<String, Object>> getMenuList() {
		return menuDAO.getMenuList();
	}
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getMenuWithItems(String menuChecked, String category) {
		if(category.equals("N")) {
			category=null;
		}
		MenuDTO menu = MenuDTO.builder()
						.menuChecked(menuChecked)
						.menuCategoryCode(category)
						.build();
		return menuDAO.getMenuWithItems(menu);
	}
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getMenuCategory() {
		return menuDAO.getMenuCategory();
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> getMenu(String itemId) {
		return menuDAO.getMenu(itemId);
	}
	
	@Transactional()
	public boolean deleteMenu(String itemId) {
		MenuDTO menu = MenuDTO.builder()
						.itemId(itemId)
						.build();
		
		// imageDAO.deleteImage(menu);
		return menuDAO.deleteMenu(menu) != 0;
	}

	// 이번달 데이터
	@Transactional(readOnly = true)
	public Map<String, Object> getChartData() {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> rawData = adminDAO.getChartData();
		List<String> month = new ArrayList<>();
		List<String> menu = new ArrayList<>();
		List<String> pc = new ArrayList<>();
		List<String> total = new ArrayList<>();
		List<String> users = new ArrayList<>();
		
		List<Map<String, Object>> rawData2 = adminDAO.getMonthlyUsers();
		
		for(Map<String, Object> data : rawData) {
			month.add(data.get("PAYMENT_MONTH").toString());
			menu.add(data.get("PT002").toString());
			pc.add(data.get("PT001").toString());
			total.add(data.get("TOTAL_SALES").toString());
		};
		
		for(Map<String, Object> data : rawData2) {
			users.add(data.get("USERCOUNT").toString());
		};
		
		result.put("month", month);
		result.put("menu", menu);
		result.put("pc", pc);
		result.put("total", total);
		result.put("users", users);
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getMonthlyUsers() {
		return adminDAO.getMonthlyUsers();
	}
	@Transactional(readOnly = true)
	public List<OrderListDTO> getOrderList() {
		return orderDAO.getOrderList();
	}
	@Transactional(readOnly = true)
	public Map<String, Object> getPieChartData() {
		Map<String, Object> result = new HashMap<>(); 
		String[] typeList = {"year", "month" ,"day"};
		
		for(String type : typeList) {
			Map<String, Object> dataList = new HashMap<>(); // 각 반복마다 초기화
			List<String> top6Menu = new ArrayList<>();
			List<String> top6Sales = new ArrayList<>();

			List<Map<String, Object>> rawData = adminDAO.getPieChartData(type);
			for(Map<String, Object> data : rawData) {
				top6Menu.add(data.get("ITEM_NAME").toString());
				top6Sales.add(data.get("TOTAL_COUNT").toString());
				
			};
			dataList.put("top6Menu", top6Menu);
			dataList.put("top6Sales", top6Sales);
			
			result.put(type, dataList);
		}
		
		System.out.println("result >>> " + result);
		return result; // year -> ITEM_NAME, TOTAL_COUNT / month -> ITEM_NAME, TOTAL_COUNT / day -> ITEM_NAME, TOTAL_COUNT
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> getUserCount() {
		Map<String, Object> result = new HashMap<>(); 
		//[{DAY=2023-11-09, USERCOUNT=1}, {DAY=2023-11-10, USERCOUNT=1}]
		Map<String, Object> dataMap = adminDAO.getUserCount();
		result.put("lastday", dataMap.get("LASTDAY"));
		result.put("today", dataMap.get("TODAY"));
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> getHourlyUsers() {
		Map<String, Object> result = new HashMap<>(); 
		String[] typeList = {"year", "month" ,"day"};
		
		for(String type : typeList) {
			Map<String, Object> dataList = new HashMap<>(); // 각 반복마다 초기화
			List<String> hours = new ArrayList<>();
			List<String> users = new ArrayList<>();
		
			List<Map<String, Object>> dataMap = adminDAO.getHourlyUsers(type);
			for(Map<String, Object> data : dataMap) {
				hours.add(data.get("HOUR").toString());
				users.add(data.get("USER_COUNT").toString());
				};
			dataList.put("users", users);
			dataList.put("hours", hours);
			result.put(type, dataList);
		};
		
		System.out.println(result);
		return result;
	}
	
	
	//@Transactional(readOnly = true)
	//@Cacheable(value = "loggedInUserList", key="'allUsers'")
	public List<UserDTO> getLoggedInUserList() {
		//System.out.println("캐싱완료!!!");
		return adminDAO.getLoggedInUserList();
	}

	public boolean shutDown() {
		adminDAO.shutDown();
		return true;
	}
	
	@Scheduled(cron = "59 59 23 L * ?")
	@CacheEvict(value = "chartData", key="'lastMonthsData'")
	public void insertChartData() {
	    System.out.println("이번달 차트 데이터 insert");
	    adminDAO.insertChartData();
	    adminDAO.updateChartDataUser();
	}

	// 지난달까지의 데이터
	@Transactional(readOnly = true)
	@Cacheable(value = "chartData", key="'lastMonthsData'")
	public Map<String, Object> getChartData2() {
		System.out.println("대시보드 캐싱 완료!!!");
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> rawData = adminDAO.getChartData2();
		
		List<String> month = new ArrayList<>();
		List<String> menu = new ArrayList<>();
		List<String> pc = new ArrayList<>();
		List<String> total = new ArrayList<>();
		List<String> users = new ArrayList<>();
		
		for(Map<String, Object> data : rawData) {
			month.add(data.get("YEAR_MONTH").toString());
			menu.add(data.get("SALES_FOOD").toString());
			pc.add(data.get("SALES_PC").toString());
			total.add(data.get("SALES_TOTAL").toString());
			users.add(data.get("USERS").toString());
		};
		result.put("month", month);
		result.put("menu", menu);
		result.put("pc", pc);
		result.put("total", total);
		result.put("users", users);
		return result;
	}
	
	
	
}
