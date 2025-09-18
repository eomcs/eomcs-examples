// 프로퍼티 값 지정하기
package com.eomcs.spring.ioc.ex04.a;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer =
        new ClassPathXmlApplicationContext("com/eomcs/spring/ioc/ex04/a/application-context.xml");

    // 객체가 생성될 때 생성자와 세터가 호출되는 것을 확인한다.
  }

}


