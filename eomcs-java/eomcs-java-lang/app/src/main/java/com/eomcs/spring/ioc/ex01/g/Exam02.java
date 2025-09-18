// IoC 컨테이너에서 객체 꺼내기 II - 예외 발생
package com.eomcs.spring.ioc.ex01.g;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam02 {
  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex01/g/application-context.xml");

    // 현재 IoC 컨테이너에 들어 있는 객체를 출력해 보자.
    SpringUtils.printBeanList(iocContainer);

    // 존재하지 않는 객체 꺼내기
    System.out.println(iocContainer.getBean("c2"));

    // 해당 이름의 객체가 들어 있지 않다면,
    // => null을 리턴하는 것이 아니라 예외가 발생한다.

    System.out.println("실행 완료!");
  }
}


