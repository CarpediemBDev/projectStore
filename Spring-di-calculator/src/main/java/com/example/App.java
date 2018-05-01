package com.example;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import com.example.app.Argument;
import com.example.app.ArgumentResolver;
import com.example.app.Calculator;

@EnableAutoConfiguration
@Import(AppConfig.class) //-> javaconfig파일을 읽어오기위한 어노테이션
public class App {

	public static void main(String[] args) {
		try(ConfigurableApplicationContext context = 
				SpringApplication.run(App.class, args); //@EnableAutoConfiguration 붙인 클래스 지정하여 @Import 로 Config 파일 찾기 
				
				//ConfigurableApplicationContext context2 = 
				//new AnnotationConfigApplicationContext(AppConfig.class);  // @Import 어노테이션을 사용하지않고 사용가능
				
				//ConfigurableApplicationContext context3 =
						//(AnnotationConfigApplicationContext) SpringApplication.run(AppConfig.class, args);
				//-> AnnotationConfigApplicationContext new 생성하지 않고 스프링부트 어플리케이션을 실행하여 컨테이너정보를 가져온다.
			
			){
			
			Scanner input = new Scanner(System.in);
			System.out.println("Enter 2 numbers like 'a b ' :");
			
			int a = input.nextInt();
			int b = input.nextInt();
			
			
			/*
			 * Di 사용법 3가지 유형...
			 * DI컨테이너에서 명시적으로 javaConfig에서 bean 가져오기
			 * */
			Calculator calculator = context.getBean(Calculator.class);
			//Calculator calculator2 = context2.getBean(Calculator.class);
			//Calculator calculator3 = context3.getBean(Calculator.class);
			
			int result = calculator.calc(a, b);
			System.out.println("result ="+result);
			
			//int result2 = calculator2.calc(a, b);
			//System.out.println("result ="+result2);
			
			//int result3 = calculator3.calc(a, b);
			//System.out.println("result ="+result3);
			
		}
	}

}
