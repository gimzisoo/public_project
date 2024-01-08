package com.kr.pub.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kr.pub.dto.OrderDTO;
import com.kr.pub.dto.UserDTO;


@Mapper
public interface UserDAO {

	public List<Map<String, Object>>erpUserList();
	
	public List<UserDTO> getUserList();

	public UserDTO getUser(UserDTO user);

	public UserDTO getUser(String userId);
	
	public UserDTO login(UserDTO user);
	
	public void updateLoginTime(UserDTO user);
	
	public int getRemainingTime(String string);
	
	public int getRemainingTime(UserDTO user);
	
	public void updateAllTime(UserDTO user);

	public UserDTO loginHistory(UserDTO user);
	
	public void loginSeat(UserDTO user);
	
	public void logoutSeat(UserDTO user);

	public UserDTO findByUserId(String userId);

	public UserDTO findByEmail(String email);

	public void insertMember(UserDTO user);
	
	public void insertUserHistory(UserDTO user);
	
	public void insertUserHistory(OrderDTO order);
	
	public void updateUserHistory(UserDTO user);
	
	public void updateRemainingTime(OrderDTO order);
	
	public void updateRemainingTime(UserDTO user);
	
	public void insertMember2(UserDTO user);

	public UserDTO loginCheck(UserDTO user);
	
	public UserDTO loginCheck(String username);

	public void failLogin(String username);
	
	public void updateRole(String username);

	public int getLoginFailures(String username);

	public String getUserRole(String username);


}
