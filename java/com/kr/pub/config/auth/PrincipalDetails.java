package com.kr.pub.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kr.pub.dao.UserDAO;
import com.kr.pub.dto.UserDTO;

import lombok.ToString;


@ToString
public class PrincipalDetails implements UserDetails {

    private static final long serialVersionUID = -951226953749557253L;
	private UserDTO user;
	


    public PrincipalDetails(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user != null ? user.getPassword() : "";
    }

    @Override
    public String getUsername() {
        return user != null ? user.getUserId() : "";
    }
    
    public int getRemainingTime() {
    	return user != null ? user.getRemainingTime() : 0;
    }

    
    public String getEmail() {
    	return user != null ? user.getEmail() : "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoleList().forEach(r -> {
            authorities.add(() -> {
                return r;
            });
        });
        
        authorities.forEach(a -> {
        	System.out.println("3 getAuthority() -> " + a.getAuthority());
        });
        System.out.println("getAuthorities()-->" + authorities);
        
        return authorities;
    }
}
