// 클래스를 이용하여 스프링 설정하기
package com.eomcs.spring.ioc.ex10.a;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer =
        new AnnotationConfigApplicationContext(AppConfig.class);

    SpringUtils.printBeanList(iocContainer);
  }

}


