// 객체 생성 - 익명 객체의 이름
package com.eomcs.spring.ioc.ex02.d;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex02/d/application-context.xml");

    System.out.println("[빈 이름]");
    String[] names = iocContainer.getBeanDefinitionNames();
    for (String name : names) {
      System.out.println(name);
    }
    // 클래스마다 인덱스는 0부터 시작한다.
  }

}


