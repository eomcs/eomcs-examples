// 프로퍼티 에디터 - 기본으로 내장된 프로퍼티 에디터
package com.eomcs.spring.ioc.ex07.a;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex07/a/application-context.xml");

    System.out.println(iocContainer.getBean("c1"));
    // 실행 오류 발생!
    // 이유?
    // java.lang.String 값을 java.sql.Date 객체로 변환할 수 없어서!
    // 즉 스프링 IoC 컨테이너에 String을 Date으로 바꾸는 변환기가 설치되어 있지 않다.
    //
    // 프로퍼티 에디터?
    // => String 값을 다른 타입의 값으로 변환하는 객체
    //
    // 스프링 빌트인 프로퍼티 에디터(변환기)
    // => primitive 타입 변환기는 기본으로 내장하고 있다.
    // => 따라서 String을
    // byte, short, int, long, float, double, boolean, char로 바꿀 수 있다.
    // => 그 외의 타입은 개발자가 프로퍼티 에디터를 만들어 등록해야 한다.
  }

}


