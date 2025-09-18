// IoC 컨테이너에 보관된 객체를 확인하기
package com.eomcs.spring.ioc.ex01.d;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam02 {
  public static void main(String[] args) {
    ApplicationContext iocContainer = new AnnotationConfigApplicationContext(
        AppConfig.class);

    // 현재 IoC 컨테이너에 들어 있는 객체를 출력해 보자.
    SpringUtils.printBeanList(iocContainer);

    // AnnotationConfigApplicationContext는
    // 애노테이션 처리에 필요한 도구를 기본 포함하고 있다.
    // 또한 Java Config 클래스(AppConfig)의 객체도 포함한다.


    System.out.println("실행 완료!");
  }
}


