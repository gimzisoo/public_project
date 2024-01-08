package com.kr.pub.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.kr.pub.dto.UserDTO;
import com.kr.pub.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        if (authentication != null && authentication.getPrincipal() != null) {
            System.out.println("authentication ->" + authentication);

            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDTO userInfo = ((PrincipalDetails) principal).getUser();

                try {
                    userService.logout(userInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("로그아웃 사용자 : " + authentication.getName());
            }
        } else {
            System.out.println("로그아웃 할 사용자가 없습니다.");
        }

        // 로그아웃 후 로그인 페이지로 리디렉션
        response.sendRedirect("/loginForm");
    }

}