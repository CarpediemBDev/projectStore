package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import com.example.app.Frontend;

/**
 * DI컨테이너에서 명시적으로 javaConfig에서 bean 가져오기 
 * Frontend 메소드 호출만 하고있음 엔트리 포인트 부분이 간결해짐 호출 부위에서는 어떤작업을하는지 알필요가없음
 * */
@EnableAutoConfiguration
@Import(AppConfig.class) //-> javaconfig파일을 읽어오기위한 어노테이션
public class App3 {

	public static void main(String[] args) {
		try(ConfigurableApplicationContext context = 
				SpringApplication.run(App3.class, args); //@EnableAutoConfiguration 붙인 클래스 지정하여 @Import 로 Config 파일 찾기 
			){
			Frontend frontend = context.getBean(Frontend.class);
			frontend.run();
			
		}
	}

}
