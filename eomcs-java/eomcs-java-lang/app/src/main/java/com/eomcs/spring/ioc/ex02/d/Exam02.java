// 객체 생성 - 익명 객체의 별명
package com.eomcs.spring.ioc.ex02.d;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam02 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex02/d/application-context.xml");

    SpringUtils.printBeanAliases(iocContainer,
        "com.eomcs.spring.ioc.ex02.Car#0");
    // 같은 클래스에 대해 첫 번째 익명 객체 만이 별명을 갖는다.
    System.out.println("------------------");

    SpringUtils.printBeanAliases(iocContainer,
        "com.eomcs.spring.ioc.ex02.Car#1");
    // 같은 클래스에 대해 두 번째 익명 객체부터는 별명이 없다.

  }

}


