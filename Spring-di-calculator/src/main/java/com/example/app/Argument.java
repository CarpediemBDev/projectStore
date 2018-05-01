package com.example.app;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data//-> 만능 어노테이션 getxxx setxxx 메소드 구현, 생성자 자동생성 등 
@RequiredArgsConstructor//-> final or @NonNull 지정된 필드값을 파라메터로 받는 생성자 자동생성
public class Argument {
	private final  int a;
	private final  int b;

}
