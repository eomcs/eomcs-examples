// 팩토리 메서드 호출 - FactoryBean 구현체
package com.eomcs.spring.ioc.ex06.d;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex06/d/application-context.xml");

    System.out.println(iocContainer.getBean("c1").getClass().getName());
    System.out.println(iocContainer.getBean("c1"));
  }

}


