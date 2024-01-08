package com.kr.pub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kr.pub.dao.UserDAO;
import com.kr.pub.dto.UserDTO;
/*import com.kr.pub.util.DummyData;*/

import jakarta.servlet.ServletContext;

@Controller
public class ReferenceController {
	@Autowired
	private ServletContext app;
	
	@Autowired
	private UserDAO userDAO;
	
	@GetMapping("/pos")
	@SuppressWarnings("unchecked")
	public String pos(Model model) {
		//Map<String, Object> result = new HashMap<>();
		//result.put("result", (List<UserDTO>) app.getAttribute("loggedInUserList"));
		List<UserDTO> loggedInUserList = (List<UserDTO>) app.getAttribute("loggedInUserList");
		model.addAttribute("loggedInUserList", loggedInUserList);
		System.out.println(loggedInUserList);
		return "/reference/pos";
	}
	

}
