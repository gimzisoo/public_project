package com.kr.pub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kr.pub.dao.AdminDAO;
import com.kr.pub.dto.UserDTO;

@Service
public class CacheService {

	@Autowired
	AdminDAO adminDAO;
	
	@Autowired
	CacheManager cacheManager;
	
	@Transactional(readOnly = true)
	@Cacheable(value = "loggedInUserList", key="'allUsers'")
	public List<UserDTO> loggedInUserList(){
		System.out.println("캐싱완료!!!");
		//System.out.println("CACHE=>"+cacheManager.getCache("loggedInUserList").get(""));
		return adminDAO.getLoggedInUserList();
	}
}
