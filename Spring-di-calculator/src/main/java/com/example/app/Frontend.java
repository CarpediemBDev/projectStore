package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;

public class Frontend {
	
	@Autowired//-> DI 컨테이너가 argumentResolver 필드에 맞는 객체를 자동으로 찾아줌
	ArgumentResolver argumentResolver;
	
	@Autowired
	Calculator calculator;
/*
 * 계산 작업 수행
 * */
	public void run() {
		System.out.println("Enter 2 numbers like 'a b' :");
		Argument argument = argumentResolver.resolve(System.in);
		int result = calculator.calc(argument.getA(), argument.getB());
		System.out.println("result =" + result);
	}
}
