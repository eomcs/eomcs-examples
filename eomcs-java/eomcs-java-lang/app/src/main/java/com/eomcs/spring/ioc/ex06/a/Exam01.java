// 팩토리 메서드 호출 - static 메서드 호출
package com.eomcs.spring.ioc.ex06.a;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {

  public static void main(String[] args) {
    // 자바 코드에서 팩토리 메서드를 사용하여 객체 생성하기
    //Car c = CarFactory.create("티코");

    // Spring IoC 컨테이너에서 팩토리 메서드를 사용하여 객체 생성하기
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex06/a/application-context.xml");

    System.out.println(iocContainer.getBean("c1"));
    System.out.println(iocContainer.getBean("c2"));
    System.out.println(iocContainer.getBean("c3"));
  }

}


