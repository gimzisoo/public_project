package com.kr.pub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kr.pub.dto.ChatDTO;

@Mapper
public interface ChatDAO {
	int insert(ChatDTO chatDTO);

	List<ChatDTO> getChatList();
	List<ChatDTO> getChatListById(ChatDTO chatDTO);

}
