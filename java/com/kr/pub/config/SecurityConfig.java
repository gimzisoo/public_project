package com.kr.pub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kr.pub.config.auth.AuthFailureHandler;
import com.kr.pub.config.auth.AuthLogoutSuccessHandler;
import com.kr.pub.config.auth.AuthSucessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private AuthSucessHandler authSucessHandler;
    
    @Autowired
    private AuthFailureHandler authFailureHandler;
    
    @Autowired
    private AuthLogoutSuccessHandler authLogoutSuccessHandler; 

    @Autowired
    private PasswordEncoder passwordEncoder;
    
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    
    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user/**").hasAnyAuthority("RT001", "RT004")
				.requestMatchers("/admin/**").hasAnyAuthority("RT004")
                .anyRequest().permitAll())
            .formLogin(formLogin -> formLogin
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")
                .successHandler(authSucessHandler)
                .failureHandler(authFailureHandler)
                .permitAll())
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(authLogoutSuccessHandler)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll())
            .sessionManagement(sessionManagement -> sessionManagement
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/loginForm?error=true&exception=Have been attempted to login from a new place. or session expired"))
            .rememberMe(rememberMe -> rememberMe
                .alwaysRemember(false)
                .tokenValiditySeconds(43200)
                .rememberMeParameter("remember-me"));

        return http.build();
    }
	
	 public static void main(String [] args) { BCryptPasswordEncoder encoder = new
	 BCryptPasswordEncoder(); 
	 System.out.println(encoder.encode("1234")); 
	 System.out.println(encoder.encode("1234")); 
	 System.out.println(encoder.encode("1234")); 
	 System.out.println(encoder.encode("1234")); 
	 }

}
