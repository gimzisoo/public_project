package com.kr.pub.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.kr.pub.config.auth.PrincipalDetails;
import com.kr.pub.dao.ItemDAO;
import com.kr.pub.dao.MenuDAO;
import com.kr.pub.dao.OrderDAO;
import com.kr.pub.dao.PaymentDAO;
import com.kr.pub.dao.SeatDAO;
import com.kr.pub.dao.UserDAO;
import com.kr.pub.dto.OrderDTO;
import com.kr.pub.dto.OrderHistoryDTO;
import com.kr.pub.dto.UserDTO;
import com.kr.pub.exception.ExistMemberException;
import com.kr.pub.util.TimeApi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private MenuDAO menuDAO;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private ItemDAO itemDAO;
	@Autowired
	private MqttService mqttService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public UserDTO findByUserId(String userId) {
		return userDAO.findByUserId(userId);
	}
	
	public List<UserDTO> getUserList() {
		return userDAO.getUserList();
	}
	
	public UserDTO getUser(UserDTO user) {
		return userDAO.getUser(user);
	}
	
	public UserDTO getUser(String userId) {
		return userDAO.getUser(userId);
	}
	
	public UserDTO login(UserDTO user,HttpServletRequest httpServletRequest, Authentication authentication) {
        // 로그인 로직 구현
        UserDTO rs = userDAO.login(user);

        // 잔여시간
        int remainingTime = getRemainingTime(user);
        
        if (rs != null) {
            if (remainingTime != 0) {
                // 잔여시간이 있을 경우
                Timestamp loginTime = TimeApi.encodingTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")));
                rs.setLoginTime(loginTime);
                
                // 사용자 ip 받아와서 좌석번호 가져오는 로직
//                String ip = GetIpAddress.getLocation(request);
//                int seatNo = seatDAO.getSeatNo(ip);
//                rs.setSeatNo(seatNo);
                
                // 랜덤 숫자로 자리 세팅
				
				 Random random = new Random(); // 1부터 50 사이의 랜덤 숫자 생성 
				 int randomNumber = random.nextInt(50) + 1; rs.setSeatNo(randomNumber);//테스트를 위해
				 // rs.setSeatNo(randomNumber);//테스트를 위해 랜덤 숫자 생성
				 
                PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
                // PrincipalDetails의 정보 업데이트
                principalDetails.getUser().setSeatNo(randomNumber);
                // SecurityContextHolder에서 Authentication 객체 업데이트
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                updateLoginTime(rs);
                userDAO.insertUserHistory(rs);
            }
        }
        
        return rs;
    }
	
	public void insertMember(UserDTO user) throws Exception {
		try {
			if (user == null ||
				Objects.isNull(user.getEmail())) {
				throw new Exception("아이디는 필수 정보입니다");
			} else if (Objects.isNull(user.getOauth()) && Objects.isNull(user.getPassword())) {
				throw new Exception("비밀번호는 필수 정보입니다");
			}
			UserDTO existMember = userDAO.findByEmail(user.getEmail());
			if (existMember != null && !Objects.isNull(user.getEmail())) {
				
				System.out.println(" >>>>> " + existMember);
				throw new ExistMemberException(existMember);
			}
			//비밀번호 암호화 한다
			//비밀번호가 있을때만 암호화를 진행한다.
			if(Objects.isNull(user.getOauth())) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			userDAO.insertMember(user);
			System.out.println(user);
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw ex;
		}
	}
	
	//@CacheEvict(value = "loggedInUserList", key="'allUsers'")
	public Map<String, Object> login2(@RequestBody UserDTO user,HttpServletRequest request,HttpServletResponse response,Authentication authentication) throws Exception {
		Map<String, Object> map = new HashMap<>();
        UserDTO rs = login(user, request, authentication);
        System.out.println(rs);
        	
		if (rs.getRemainingTime() > 0) {
			map.put("rs", rs);
			map.put("message", "로그인 성공했습니다.");
			// 좌석정보 가져오는 루틴 필요(밑의 함수 파라미터에 넣어주기)
			loginSeat(rs);// random번 사용중으로 변경
			JSONObject jsonObject = new JSONObject(Map.of("type", "LOGIN", "receiver", "admin"));
			mqttService.publishMessage(jsonObject.toString(), "/public/login");// 로그인한 알림 관리자에게
		} else if (rs.getRemainingTime() == 0) {
			map.put("message", "잔여시간이 없습니다.");
			map.put("rs", 0);
		} else {
			map.put("message", "로그인 실패했습니다.");
		}

        return map;
	}
    
	public void updateLoginTime(UserDTO user) {
    	userDAO.updateLoginTime(user);
    }
    
    public int getRemainingTime(UserDTO user) {
    	return userDAO.getRemainingTime(user);
    }

	public void updateAllTime(UserDTO user) {
		userDAO.updateAllTime(user);
		
	}
	public void loginSeat(UserDTO user) {
		userDAO.loginSeat(user);
	}
	public void logoutSeat(UserDTO user) {
		userDAO.logoutSeat(user);
	}


	public UserDTO loginHistory(UserDTO user) {
		return userDAO.loginHistory(user);
	}

	//@Transactional
	//@CacheEvict(value = "loggedInUserList", key="'allUsers'")
	public void logout(UserDTO userInfo) throws Exception {
		UserDTO logoutUser = getUser(userInfo);
	        if (logoutUser != null) {
	            int remainingTime = logoutUser.getRemainingTime();
	            // 입장시간
	            Timestamp loginTime = logoutUser.getLoginTime();
	            // 퇴장시간
	            Timestamp logoutTime = TimeApi.encodingTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")));
	            // 입장시간 - 퇴장시간
	            Duration setDuration = Duration.between(loginTime.toInstant(), logoutTime.toInstant());
	            // 잔여시간 - 사용시간
	            int seconds = (int) setDuration.getSeconds();
	            remainingTime = (remainingTime - seconds);
	            logoutUser.setLogoutTime(logoutTime);
	            userDAO.updateUserHistory(logoutUser);
	            if (remainingTime <= 0) {
	                logoutUser.setRemainingTime(0);
	            } else {
	                logoutUser.setRemainingTime(remainingTime);
	            }
	            updateAllTime(logoutUser); // DB에 업데이트
	            System.out.println(logoutUser);
	            logoutSeat(logoutUser);

	            JSONObject jsonObject = new JSONObject(Map.of(
	                    "type", "LOGOUT",
	                    "receiver", "admin"
	            ));
	            mqttService.publishMessage(jsonObject.toString(), "/public/logout"); // 로그아웃한 알림 관리자에게
	        } else {
	            // 해당 사용자 정보를 찾을 수 없는 경우에 대한 처리
	            System.out.println("User not found in loggedInUserList");
	        }
	    return; // 로그인 페이지로 리다이렉트
	}
	
	public int rechargeTime(UserDTO user) {
		System.out.println(">>> " +user);
	    int remainingTime = userDAO.getRemainingTime(user); 
	    System.out.println(">>> " + remainingTime);
	    int rechargeTime = user.getRemainingTime(); 
	    
	    remainingTime += rechargeTime; 
        user.setRemainingTime(remainingTime);
        userDAO.updateRemainingTime(user); 
        
	    if (rechargeTime > 0) { 
	        return 1; 
	    } else {
	        return 0; 
	    }
	}

	//@CacheEvict(value = "loggedInUserList", key="'allUsers'")
	public void chargeTime(OrderDTO order) {
		int remainingTime = userDAO.getRemainingTime(order.getUserId()); 
		System.out.println(remainingTime);

	    remainingTime += order.getRemainingTime();
	    UserDTO user = new UserDTO();
	    order.setUserId(order.getUserId());
	    order.setRemainingTime(remainingTime);
	    
	    System.out.println("시간" + remainingTime);
	    System.out.println("아이디" + order.getUserId());
	    userDAO.updateRemainingTime(order);
	}

	
	public List<Map<String, Object>> getMenuList() {
		return menuDAO.getMenuList();
	}


	
	public String insertOrder(OrderDTO order) {
        orderDAO.insertOrder(order);
		/* paymentDAO.insertPayment(order); */
        return order.getOrderId();
    }

	public void insertOrderHistory(String orderId, List<OrderHistoryDTO> cartItems) {
	    
		System.out.println(orderId);
		System.out.println(cartItems);
		
		for (OrderHistoryDTO item : cartItems) {
	        OrderHistoryDTO orderHistory = OrderHistoryDTO.builder()
	            .orderId(orderId)
	            .itemId(item.getItemId())
	            .quantity(item.getQuantity())
	            .price(item.getPrice())
	            .build();

	        orderDAO.insertOrderHistory(orderHistory);
	    }
	}

	
	public void insertChargeOrderHistory(OrderDTO order,Map<String, Object> requestData) {
	    String orderId = order.getOrderId();
	    List<Map<String, Object>> cartItemsMapList = (List<Map<String, Object>>) requestData.get("items");
	    System.out.println(cartItemsMapList);
	    List<OrderHistoryDTO> cartItems = new ArrayList<>();
	    
	    
	    for (Map<String, Object> itemMap : cartItemsMapList) {
	        OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO();
	        orderHistoryDTO.setOrderId(orderId);
	        orderHistoryDTO.setItemId((String) itemMap.get("itemId"));
	        orderHistoryDTO.setItemId((String) itemMap.get("itemId"));
	        orderHistoryDTO.setQuantity((int) itemMap.get("quantity"));
	        orderHistoryDTO.setPrice((int) itemMap.get("price"));

	        cartItems.add(orderHistoryDTO);
	        System.out.println(cartItems);
	    }
	    orderDAO.insertChargeOrderHistory(cartItems);
	}
	
	public void updateItemStock(List<OrderHistoryDTO> cartItems) {
		
		for (OrderHistoryDTO item : cartItems) {
	        OrderHistoryDTO orderHistory = OrderHistoryDTO.builder()
	            .itemId(item.getItemId())
	            .quantity(item.getQuantity())
	            .build();

	        itemDAO.updateItemStock(orderHistory);
	    }
	}
	
	public void failLogin(String username) {
        userDAO.failLogin(username);
    }

    public void updateRole(String username) {
    	String userRole = userDAO.getUserRole(username);
    	System.out.println("사용자 롤 " + userRole);
    	int loginFailures = userDAO.getLoginFailures(username);

        if (loginFailures >= 3) {
        	if(userRole.equals("RT001")) {
        		userDAO.updateRole(username);
        	}
        }
    }
}
