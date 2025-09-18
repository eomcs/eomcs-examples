// 클래스를 이용하여 스프링 설정하기 - @Configuration 애노테이션
package com.eomcs.spring.ioc.ex10.b;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) {
    // 패키지명을 지정하면
    // 해당 패키지의 모든 클래스를 찾아
    // @Component, @Service, @Controller, @Repository
    // 애노테이션이 붙은 클래스에 대해 객체를 자동 생성한다.
    // 또한,
    // @Configuration 애노테이션이 붙은 클래스를 찾아 객체를 생성한다.
    // 그리고 그 클래스에 @Bean 애노테이션이 붙은 메서드를 호출하여
    // 그 리턴 값을 저장한다.
    //
    ApplicationContext iocContainer = new AnnotationConfigApplicationContext(
        "com.eomcs.spring.ioc.ex10.b");

    SpringUtils.printBeanList(iocContainer);
  }

}


