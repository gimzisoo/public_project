package com.kr.pub.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kr.pub.dto.ChatDTO;
import com.kr.pub.service.ChatService;

@Controller
@RequestMapping("/chat")
public class ChatController {
	@Autowired
	ChatService chatService;
	
	@GetMapping("/list")
	@ResponseBody
	public Map<String, List<ChatDTO>> chatList() {
		Map<String, List<ChatDTO>> result = new HashMap<>();
		result.put("result", chatService.getChatList());
		return result;
	}
	@PostMapping("/getListById")
	@ResponseBody
	public Map<String, List<ChatDTO>> getListById(@RequestBody ChatDTO chatDTO) {
		System.out.println("getListById" + chatDTO);
		Map<String, List<ChatDTO>> result = new HashMap<>();
		result.put("result", chatService.getChatListById(chatDTO));
		return result;
	}
}
