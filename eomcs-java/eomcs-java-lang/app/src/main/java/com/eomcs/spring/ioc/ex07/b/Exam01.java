// 프로퍼티 에디터 - String ==> java.sql.Date ; 변환기 없이 처리하기
package com.eomcs.spring.ioc.ex07.b;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex07/b/application-context.xml");

    System.out.println(iocContainer.getBean("c1"));

    //java.sql.Date date = java.sql.Date.valueOf("2023-9-1");
  }

}


