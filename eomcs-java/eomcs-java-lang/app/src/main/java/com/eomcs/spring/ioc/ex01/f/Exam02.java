// IoC 컨테이너에 객체를 보관하기 II
package com.eomcs.spring.ioc.ex01.f;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam02 {
  public static void main(String[] args) {
    ApplicationContext iocContainer = new AnnotationConfigApplicationContext(
        AppConfig.class);

    // 현재 IoC 컨테이너에 들어 있는 객체를 출력해 보자.
    SpringUtils.printBeanList(iocContainer);

    // 애노테이션을 처리하는 기본 도구 외에
    // AppConfig에서 설정한 객체(Car)도 포함하고 있다.

    System.out.println("실행 완료!");
  }
}


