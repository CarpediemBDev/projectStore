package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication // @Configuration + @EnableAutoConfiguration + @ComponentScan 3가지 묶음 
// 해당 어노테이션들은 다양한 설정이 자동으로 수행된다. 기존의 스프링 설정파일이 필요없게됨
public class DemoApplication {
	
	@RequestMapping("/") 
	public String home() {
		return "Hello Spring Boot";
	}
	// 추가로 신규 url 추가
	@RequestMapping("/new")
	public String newMethod() {
		return "New Url";
	}


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
