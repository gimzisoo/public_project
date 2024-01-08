package com.kr.pub.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kr.pub.config.MqttConfig;
import com.kr.pub.dto.ChatDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MqttService {
	@Autowired
	MqttConfig.OutboundGateway outboundGateway;
	@Autowired
	ChatService chatService;
	@Autowired
	CacheManager cacheManager;

	public void publishMessage(String message, String topic) throws Exception {
		outboundGateway.sendToMqtt(message, topic);
	}

	public void execute(String topic, String payload) throws Exception {// MQTT 메세지 수신시 실행
		System.out.println(topic);
		String[] cmdParams = StringUtils.split(topic, "/");
		if(cmdParams.length >= 2) {
			final String command = cmdParams[1];
			if (command.equals("chat")) {
				ChatDTO chat = new ObjectMapper().readValue(payload, ChatDTO.class);
				System.out.println("MQTTSERVICE=>" + chat);
				System.out.println("CHATINSERT!!!!");
				chatService.insert(chat);
			}
//				else if(command.equals("charge") || command.equals("login") || command.equals("logout")) {
//			 	cacheManager.getCache("loggedInUserList").evict("'allUsers'");
//			 	System.out.println("loggedInUserList 캐시 초기화!!!");
//			}
		}
	}
}
