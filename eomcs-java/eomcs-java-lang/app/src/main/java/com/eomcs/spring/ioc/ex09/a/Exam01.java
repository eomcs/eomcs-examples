// 객체 자동 생성 - @Component 애노테이션
package com.eomcs.spring.ioc.ex09.a;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(
        "com/eomcs/spring/ioc/ex09/a/application-context.xml");

    SpringUtils.printBeanList(iocContainer);
  }

}


