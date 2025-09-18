// 생성자의 파라미터 값을 지정하는 방법 III
package com.eomcs.spring.ioc.ex03.d;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex03/d/application-context.xml");

    // SpringUtils.printBeanNames(iocContainer);
  }

}


