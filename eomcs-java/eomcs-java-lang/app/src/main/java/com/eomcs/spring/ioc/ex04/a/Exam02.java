// 프로퍼티 값이 제대로 설정되었는지 확인하기
package com.eomcs.spring.ioc.ex04.a;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam02 {

  public static void main(String[] args) {
    ApplicationContext iocContainer =
        new ClassPathXmlApplicationContext("com/eomcs/spring/ioc/ex04/a/application-context.xml");

    // 객체의 프로퍼티 값이 제대로 설정되었는지 확인한다.

    System.out.println(iocContainer.getBean("c1"));
    System.out.println(iocContainer.getBean("c2"));
    System.out.println(iocContainer.getBean("c3"));
    System.out.println(iocContainer.getBean("c4"));
  }

}


