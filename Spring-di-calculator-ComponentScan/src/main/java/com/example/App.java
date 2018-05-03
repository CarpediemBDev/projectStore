package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.example.app.Frontend;

/**
 * DI컨테이너에서 명시적으로 javaConfig에서 bean 가져오기 
 * @ComponentScan으로 javaConfig파일 등록없이 bean 등록이 가능
 * */
@EnableAutoConfiguration
@ComponentScan
public class App  {

	public static void main(String[] args) {
		try(ConfigurableApplicationContext context = 
				SpringApplication.run(App.class, args); //@EnableAutoConfiguration 붙인 클래스 지정하여 @Import 로 Config 파일 찾기 
			){
			Frontend frontend = context.getBean(Frontend.class);
			frontend.run();
			
		}
	}

}
