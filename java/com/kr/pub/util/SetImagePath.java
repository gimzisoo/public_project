package com.kr.pub.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jakarta.servlet.ServletContext;


public class SetImagePath {
	

//	private static ServletContext servletContext;
//	
//	public static String setImagePath() {
//		
//		String classpath = System.getProperty("java.class.path"); // 클래스 패스
//		String os = System.getProperty("os.name").toLowerCase(); // os 정보
//		// "public_project" 까지의 클래스 패스 가져오기
//		int endIndex = classpath.indexOf("public_project") + "public_project".length();
//		String pathUpToPublicProject = classpath.substring(0, endIndex);
//		
//		// static 이미지 폴더 경로 추가
//		String addPath = os.contains("win") ? "\\src\\main\\resources\\static\\images\\menu\\" : "/src/main/resources/static/images/menu/";
//		String newPath = pathUpToPublicProject + addPath;
//		
//		// 어플리케이션 영역에 경로 저장
//		servletContext.setAttribute("newPath", newPath);
//		
//		return newPath;
//		
//	}

}
