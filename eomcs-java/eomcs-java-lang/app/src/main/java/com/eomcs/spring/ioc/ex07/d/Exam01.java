// 프로퍼티 에디터 - String ==> Engine ; 프로퍼티 에디터 변환기를 사용하여 처리하기
package com.eomcs.spring.ioc.ex07.d;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex07/d/application-context.xml");

    System.out.println(iocContainer.getBean("c1"));

    System.out.println("----------------------------");

    // SpringUtils.printBeanNames(iocContainer);
  }

}


