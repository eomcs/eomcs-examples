// 객체 자동 생성 - 특정 패키지의 클래스에 대해 객체 생성하지 않기
package com.eomcs.spring.ioc.ex09.c;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex09/c/application-context.xml");

    SpringUtils.printBeanList(iocContainer);
  }

}


