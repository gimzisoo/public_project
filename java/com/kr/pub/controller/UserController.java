package com.kr.pub.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kr.pub.config.auth.PrincipalDetails;
import com.kr.pub.dto.OrderDTO;
import com.kr.pub.dto.OrderHistoryDTO;
import com.kr.pub.dto.PaymentDTO;
import com.kr.pub.dto.UserDTO;
import com.kr.pub.service.PaymentService;
import com.kr.pub.service.UserService;

import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private ServletContext servletContext;
	
	@Value("${file.upload.directory}")
	private String filePath;
	
	/*
	 * 순서:
	 * 1.사용자가 주문 버튼 누름
	 * 2.DB에 insert하는 api호출
	 * 3.Admin에게 알림 보내기
	 * 4.Admin측에서 알림이 오면 Ajax로 api호출해서
	 * update되지 않은 데이터 모두 가져와서 뷰단에 동적으로 출력
	 * 
	 * 주문이 DB에 insert 되면 Admin에게 알림 보내서
	 * insert된 Data를 Admin측에서 select
	 * select할때 화면상에 위치하는 가장 위의 데이터 보다
	 * 최근인 데이터들만 가져와서 뷰단에 동적으로 출력 
	 * */

	
	@GetMapping("")
    public String login() {
		return "/user/login";
    }
	@GetMapping("/test")
    public String testIp() {
        return "/user/ipTest";
    }
	
    @GetMapping("/main")
  	public String Main() {
    	// 어플리케이션 영역에 경로 저장
		servletContext.setAttribute("newPath", filePath);
		System.out.println(filePath);
  		return "/user/main";
  	}
    @GetMapping("/userTest")
    public String userTest() {
    	return "/user/userTest";
    }
    
	@GetMapping("/list")
	public String userList(Model model) {
		List<UserDTO> result = userService.getUserList();
		System.out.println(result);
		model.addAttribute("result",  result);
		return "/user/userList";
	}
	
	@GetMapping("/{userId}")
	public String userInfo(Model model, @PathVariable String userId) {
		UserDTO result = userService.getUser(userId);
		model.addAttribute("result", result);
		return "/user/userList";
	}
	
	@PostMapping("/getUser")
	@ResponseBody
	public Map<String, UserDTO> getUser(@RequestBody UserDTO userDTO){
		System.out.println("getUser 호출");
		System.out.println(userDTO);
		Map<String, UserDTO> result = new HashMap<>();
		result.put("result", userService.getUser(userDTO));
		return result;
	}

	@GetMapping("/getMenuList")
	@ResponseBody 
	public Map<String, Object> getMenuList(Model model) throws Exception {
	    
		List<Map<String, Object>> menuList = userService.getMenuList();
	    System.out.println("메뉴 리스트: " + menuList);

//	    ObjectMapper objectMapper = new ObjectMapper();
//	    String jsonMenuList = objectMapper.writeValueAsString(menuList);

	    Map<String, Object> rs  = new HashMap<>();
	    rs.put("menuList", menuList);
	    return rs;
	}
	

	@PostMapping("/order")
	@ResponseBody
	public Map<String, String> order(@RequestBody OrderDTO order) {
	    String userId = order.getUserId();
	    order.setUserId(userId);
	    System.out.println("사용자 아이디 : " + userId);

	    Map<String, String> map = new HashMap<>();

	    try {
	        String orderId = userService.insertOrder(order);
	        order.setOrderId(orderId);
	        System.out.println("주문아이디" + orderId);

	        List<OrderHistoryDTO> cartItems = order.getItems();
	        PaymentDTO paymentMethodCode = order.getPaymentMethodCode();
	        order.setItems(cartItems);
	        System.out.println("장바구니 목록 : " + cartItems);
	        System.out.println(paymentMethodCode);

	        userService.insertOrderHistory(orderId, cartItems);
	        userService.updateItemStock(cartItems);

	        PaymentDTO paymentDTO =  PaymentDTO.builder()
	                .paymentTypeCode("PT002")
	                .paymentMethodCode(paymentMethodCode.getPaymentMethodCode())
	                .orderId(orderId)
	                .build();
	        System.out.println(paymentDTO);
	        paymentService.insertPayment(paymentDTO);

	        map.put("rs", "true");
	    }  catch (Exception e) {
	        e.printStackTrace();
	        map.put("rs", "false");
	     }
	    return map;
	}
	
	//시간충전
	@PostMapping("/recharge")
	@ResponseBody
	public Map<String, String> recharge(@RequestBody Map<String, Object> requestData, OrderDTO order) {
	    System.out.println("시간충전");
	    String userId = (String) requestData.get("userId");
	    int remainingTime = ((Number) requestData.get("remainingTime")).intValue();
	    List<OrderHistoryDTO> cartItems = (List<OrderHistoryDTO>) requestData.get("items");
	    String paymentMethodCode = (String) requestData.get("paymentMethodCode");
	    
	    order.setRemainingTime(remainingTime);
	    order.setUserId(userId);
	    order.setItems(cartItems);


	    Map<String, String> map = new HashMap<>();

	    try {
	        String orderId = userService.insertOrder(order);
	        order.setOrderId(orderId);
	        System.out.println(order);
	        userService.chargeTime(order);
	        
	        userService.insertChargeOrderHistory(order, requestData);

	        PaymentDTO paymentDTO = PaymentDTO.builder()
	                .paymentTypeCode("PT001")
	                .paymentMethodCode(paymentMethodCode)
	                .orderId(orderId)
	                .build();
	        System.out.println(paymentDTO);
	        paymentService.insertPayment(paymentDTO);

	        map.put("rs", "true");
	    } catch (Exception e) {
	        e.printStackTrace();
	        map.put("rs", "false");
	    }
	    return map;
	}


}
