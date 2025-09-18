// 스프링 IoC 컨테이너 사용
package com.eomcs.spring.ioc.ex01.c;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Exam01 {
  public static void main(String[] args) {
    // 3) 자바 클래스 파일의 애노테이션으로부터 설정 정보를 추출한다.
    // => 자바 클래스로 설정 정보를 다루는 것을 'Java Config' 라 부른다.
    // => 생성자 파라미터로 Java Config 클래스의 타입 정보를 넘긴다.
    ApplicationContext iocContainer = new AnnotationConfigApplicationContext(
        AppConfig.class);

    System.out.println("실행 완료!");
  }
}


