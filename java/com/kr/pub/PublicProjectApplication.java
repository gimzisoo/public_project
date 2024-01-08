package com.kr.pub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PublicProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicProjectApplication.class, args);
	}

}
