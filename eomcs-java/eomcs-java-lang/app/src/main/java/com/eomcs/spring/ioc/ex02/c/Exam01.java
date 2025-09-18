// 객체 생성 - 빈 생성 정책
package com.eomcs.spring.ioc.ex02.c;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex02/c/application-context.xml");

    // IoC 컨테이너가 생성될 때,
    // "singleton" 객체는 기본으로 생성된다.

    System.out.println("[빈 이름]");
    System.out.println("빈 개수: " + iocContainer.getBeanDefinitionCount());

    String[] names = iocContainer.getBeanDefinitionNames();
    for (String name : names) {
      System.out.println(name);
    }
    // => 객체가 생성되지 않았더라도 빈의 이름은 모두 등록되어 있다.
  }

}


