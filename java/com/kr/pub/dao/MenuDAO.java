package com.kr.pub.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kr.pub.dto.ImageDTO;
import com.kr.pub.dto.MenuDTO;
import com.kr.pub.dto.OrderListDTO;

@Mapper
public interface MenuDAO {
	
	public boolean updateMenu(MenuDTO menuDTO);

	public List<Map<String, Object>> getMenuList();
	
	public List<Map<String, Object>> getMenuWithItems(MenuDTO menu);

	public List<Map<String, Object>> getMenuCategory();

	public Map<String, Object> getMenu(String itemId);

	public int deleteMenu(MenuDTO menu);

}
