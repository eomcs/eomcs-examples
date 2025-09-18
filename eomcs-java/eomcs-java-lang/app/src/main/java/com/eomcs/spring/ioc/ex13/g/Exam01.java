// AOP(Aspect-Oriented Programming) - Java Config로 AOP 설정
package com.eomcs.spring.ioc.ex13.g;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext iocContainer =
        new AnnotationConfigApplicationContext(AppConfig.class);

    SpringUtils.printBeanList(iocContainer);

    try {
      Caller caller = iocContainer.getBean(Caller.class);
      caller.test();
    } catch (Exception e) {
      System.err.println("오류 발생!");
    }
  }

}


