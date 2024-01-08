package com.kr.pub.exception;

import com.kr.pub.dto.UserDTO;

//멤버가 존재할 경우 예외 객체
public class ExistMemberException extends Exception {
	UserDTO userDTO;
	
	public ExistMemberException(UserDTO userDTO) {
		super(userDTO.getEmail() + "계정이 존재합니다");
		System.out.println(">>>>>>>>> exception >> " + userDTO);
		this.userDTO = userDTO;	
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
}
