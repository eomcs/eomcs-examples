// 생성자 호출 확인
package com.eomcs.spring.ioc.ex03.a;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex03/a/application-context.xml");

    // SpringUtils.printBeanNames(iocContainer);
  }

}


