package com.kr.pub.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.catalina.Session;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kr.pub.config.MqttConfig.OutboundGateway;
import com.kr.pub.config.auth.AuthSucessHandler;
import com.kr.pub.config.auth.PrincipalDetails;
import com.kr.pub.config.auth.PrincipalDetailsService;
import com.kr.pub.dto.KakaoProfile;
import com.kr.pub.dto.OAuthToken;
import com.kr.pub.dto.UserDTO;
import com.kr.pub.exception.ExistMemberException;
import com.kr.pub.service.MqttService;
import com.kr.pub.service.UserService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private MqttService mqttService;
	@Autowired
	private ServletContext app;
	
	@GetMapping("/")
	public String main(Model model) {
		return "login";
	}

	//로그인 양식 
	@GetMapping("/loginForm")
	public String loginForm(Model model,
			@RequestParam(value = "error", required = false) String error, 
			@RequestParam(value = "exception", required = false) String exception, HttpSession session) {
		
		System.out.println("loginForm.SessionId >>>>>" + session.getId());

		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		
		return "login";
	}
	
	
	// 잔여시간 없음
	@GetMapping("/recharge")
    public String showPayment(Model model, Authentication authentication) {
		 //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 //String userId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getUserId();
		String userId = "";
		System.out.println(" >>> " + "충전 진입");
		System.out.println("SecurityContextHolder.getContext()  >>>>>" + SecurityContextHolder.getContext());
		System.out.println("SecurityContextHolder.getContext().getAuthentication  >>>>>" + SecurityContextHolder.getContext().getAuthentication());
		
		//authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(" >>> " + authentication);
		if (authentication != null) {
			Object  principal =  authentication.getPrincipal();
			//PrincipalDetails principal =  (PrincipalDetails) authentication.getPrincipal();
			//userId = principal.getUser().getUserId();
			System.out.println(" >>>> " +  principal.getClass().getName());
			System.out.println(" >>>> " +  principal);
		}
		
        model.addAttribute("showPaymentContent", true);
        model.addAttribute("userId", userId);
        
        return "login"; 
    }
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code,HttpSession session,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse , RedirectAttributes attributes) throws Exception { // Data를 리턴해주는 컨트롤러 함수
		System.out.println("전: SecurityContextHolder.getContext().getAuthentication  >>>>>" + SecurityContextHolder.getContext().getAuthentication());
		System.out.println("code = " + code);
		String returnPath = "redirect:/";
		// POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
		// Retrofit2
		// OkHttp
		// RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "f9233fa5fb65f7da3addba11aa52d18a");
		params.add("redirect_uri", "http://localhost:8282/auth/kakao/callback");
		params.add("code", code);
		
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers); //이 부분은 변경할 필요가 없다.
		

		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
		//exchange : 클라이언트에서 서버로 값을 보내준다.
		ResponseEntity<String> response = restTemplate.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class //문자열로 받기 때문에 String.class로 써준거다. 그러면 응답 데이터가 문자열로 들어온다.
		);
		
		//응답 결과 body 출력
		System.out.println("https://kauth.kakao.com/oauth/token의 body = " + response.getBody());
		
		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token()); //<<토큰을 얻어온거 확인하기
		
		session.setAttribute("kakaoAccessToken", oauthToken.getAccess_token());

		//사용자 정보 가져오기(인가 데이터 가져오기)
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token()); // Bearer에서는 반드시 공백이 있어야 한다 없으면 에러 발생
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
		
		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class //String으로 사용자의 상세정보가 나온다.
		);
		System.out.println(response2.getBody());
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
//		// User 오브젝트 : username, password, email
//		System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
//		System.out.println("카카오 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		
		//DB에 기록하기....
		UserDTO kakaoMember = UserDTO.builder()
				.email(kakaoProfile.getKakao_account().getEmail())
				.roleId("RT001")
				.oauth("kakao")
				.build();
		UserDTO userDTO = kakaoMember;
		
		// 가입자 혹은 비가입자 체크 해서 처리
		try {
			String mail = kakaoMember.getEmail();
			int index = mail.indexOf("@"); ;
			
//			String kakaoId = new String();
//			kakaoId.substring(0, index);
			
			 String[] parts = mail.split("@");
			 String kakaoId = parts[0];
			kakaoMember.setUserId(kakaoId);
			
			System.out.println("카카오 멤버 >>>>> " + kakaoMember);
			userService.insertMember(kakaoMember);
			System.out.println(kakaoMember);
			
			System.out.println("기존 회원이 아니기에 자동 회원가입을 진행함");
			
		} catch (ExistMemberException e) {
			System.out.println("기존에 회원 가입된 경우 다음으로 진행함");
			ExistMemberException ex = (ExistMemberException)e;
			userDTO = ex.getUserDTO();
			System.out.println("userDTO >>>>"  +userDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("자동 로그인을 진행합니다.");
	
		
		
		// 로그인 처리 
		PrincipalDetails principalDetails = new PrincipalDetails(userDTO);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				principalDetails, // 나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
				null, // 토큰 인증시 패스워드는 알수 없어 null 값을 전달하는 것임  
				principalDetails.getAuthorities()); //사용자가 소유한 역할 권한을 전달한다 
        
		System.out.println("principalDetails." + principalDetails);
		System.out.println("authentication." + authentication);
		
		if(userDTO.getRemainingTime() > 0) {
			returnPath = returnPath + "user/main";
	        UserDTO rs = userService.login(userDTO, httpServletRequest, authentication);
	        System.out.println("rs 결과출력2 >>> " + rs);
	        if (rs.getRemainingTime() > 0) {
	            attributes.addAttribute("message", "로그인 성공했습니다.");
	            //좌석정보 가져오는 루틴 필요(밑의 함수 파라미터에 넣어주기)
	            userService.loginSeat(rs);//random번 사용중으로 변경
	            System.out.println("사용중 변경 완료");
	            	JSONObject jsonObject = new JSONObject(Map.of(
	            		    "type", "LOGIN",
	            		    "receiver", "admin"
	            		));
            	 Cookie cookie = new Cookie("userId", userDTO.getUserId());
                 cookie.setMaxAge(24 * 60 * 60);
                 cookie.setPath("/");
                 httpServletResponse.addCookie(cookie);
	            mqttService.publishMessage(jsonObject.toString() ,"/public/login");//로그인한 알림 관리자에게
	        }  else if(rs.getRemainingTime() == 0) {
	        	attributes.addAttribute("message", "잔여시간이 없습니다.");
	        	attributes.addAttribute("rs", 0);
		    } else {
		    	attributes.addAttribute("message", "로그인 실패했습니다.");
	        }
			
		} else {
			returnPath = returnPath + "recharge";
		}
		
		System.out.println(">>> " +returnPath);
		
		
		// 강제로 시큐리티의 세션에 접근하여 값 저장
		System.out.println("SecurityContextHolder.getContext()  >>>>>" + SecurityContextHolder.getContext());
		System.out.println("SecurityContextHolder.getContext().getAuthentication  >>>>>" + SecurityContextHolder.getContext().getAuthentication());
		System.out.println("SecurityContextHolder.getContext().getAuthentication  >>>>>" + SecurityContextHolder.getContext().getAuthentication());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("SessionId >>>>>" + httpServletRequest.getSession().getId());
		
		return returnPath;
	}
	
	// 시간 충전
    @PostMapping("/user/rechargeTime")
    @ResponseBody
    public Map<String, String> rechargeTime(@RequestBody UserDTO user) {
        Map<String, String> map = new HashMap<>();
        System.out.println("user >>> " +user);
        int rechargeResult = userService.rechargeTime(user);
        if (rechargeResult == 1) { 
            map.put("rs", "success");
            map.put("message", "시간 충전이 완료되었습니다.");
        } else { 
            map.put("rs", "fail");
            map.put("message", "시간 충전에 실패했습니다. 다시 시도해주세요.");
        }
        
        return map;
    }
    
}
