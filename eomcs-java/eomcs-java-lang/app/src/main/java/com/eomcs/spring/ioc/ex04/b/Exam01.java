// 프로퍼티 값 자동 형변환
package com.eomcs.spring.ioc.ex04.b;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer =
        new ClassPathXmlApplicationContext("com/eomcs/spring/ioc/ex04/b/application-context.xml");

    // 프로퍼티의 타입이 int 일 경우 XML에 작성한 문자열이
    // 자동으로 int 값으로 형변환된다.
    // 만약 형변환할 수 없다면 예외가 발생한다.
    // 자동 형변환은 primitive type에 대해서만 가능하다.
    // 그 외의 타입에 대해서는 문자열을 자동 형변환하지 않는다.
    // 형변환하고 싶으면 개발자가 형변환시키는 클래스를 만들어
    // 스프링 프레임워크에 등록해야 한다.
  }

}


