package com.kr.pub.config.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kr.pub.dao.UserDAO;
import com.kr.pub.dto.UserDTO;

@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService : 진입");
		
		UserDTO member = null;
		if(userDAO.loginCheck(username) == null) {
			member = userDAO.findByUserId(username);
		} 
		
		System.out.println("PrincipalDetailsService : member -> " + member);

		return new PrincipalDetails(member);
	}
}
