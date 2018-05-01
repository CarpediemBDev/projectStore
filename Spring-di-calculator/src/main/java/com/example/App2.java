package com.example;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import com.example.app.Argument;
import com.example.app.ArgumentResolver;
import com.example.app.Calculator;

/**
 * DI컨테이너에서 명시적으로 javaConfig에서 bean 가져오기 
 * 계산하는부분과 인자받는부분을 추상화하여 외부로 로직 분리
 * */
@EnableAutoConfiguration
@Import(AppConfig.class) //-> javaconfig파일을 읽어오기위한 어노테이션
public class App2 {

	public static void main(String[] args) {
		try(ConfigurableApplicationContext context = 
				SpringApplication.run(App2.class, args); //@EnableAutoConfiguration 붙인 클래스 지정하여 @Import 로 Config 파일 찾기 
			){
			System.out.println("Enter 2 numbers like 'a b ' :");
			
			/* 
			 * 아래의 Scanner를 통한 입력받는 a, b 변수값을 resolve 메소드를 통하여 Argument에 담아놓는다. 
			 * 계산하기와 인자받기 부분을 추상화하여 외부로 분리*/
			//Scanner input = new Scanner(System.in);
			//int a = input.nextInt();
			//int b = input.nextInt();
			
			ArgumentResolver argumentResolver = context.getBean(ArgumentResolver.class);
			Argument argument = argumentResolver.resolve(System.in);// 생성자를 통한 a, b 변수 생성
			Calculator calculator = context.getBean(Calculator.class);
			int result = calculator.calc(argument.getA(),argument.getB() );// a, b 변수를 get         
			System.out.println("result ="+result);
		}
	}

}
