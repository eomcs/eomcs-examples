// 객체 자동 생성 - <context:annotation-config> 태그 생략하기
package com.eomcs.spring.ioc.ex09.b;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) {

    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(
        "com/eomcs/spring/ioc/ex09/b/application-context.xml");

    SpringUtils.printBeanList(iocContainer);
  }

}


