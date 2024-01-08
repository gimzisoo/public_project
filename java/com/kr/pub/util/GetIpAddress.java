package com.kr.pub.util;

import jakarta.servlet.http.HttpServletRequest;

public class GetIpAddress {
	
	   public static String getLocation(HttpServletRequest request) {
			String ip = request.getHeader("X-Forwarded-For");
			if (ip == null) {
	           ip = request.getHeader("Proxy-Client-IP");
	           System.out.println(">>>> Proxy-Client-IP : " + ip);
	       }
	       if (ip == null) {
	           ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
	           System.out.println(">>>> WL-Proxy-Client-IP : " + ip);
	       }
	       if (ip == null) {
	           ip = request.getHeader("HTTP_CLIENT_IP");
	           System.out.println(">>>> HTTP_CLIENT_IP : " + ip);
	       }
	       if (ip == null) {
	           ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	           System.out.println(">>>> HTTP_X_FORWARDED_FOR : " + ip);
	       }
	       if (ip == null) {
	           ip = request.getRemoteAddr();
	       }
	       String[] parts = ip.split("\\.");
	        String lastPart = parts[parts.length - 1];
	       
	       System.out.println(">>>> Result : IP Address : "+ip);
	       System.out.println("ip 두자리 >>> " + lastPart);
	       return lastPart;
	   }
}
