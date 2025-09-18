// 객체 생성 - 빈 생성 정책 : singleton 객체
package com.eomcs.spring.ioc.ex02.c;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.ex02.Car;

public class Exam02 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex02/c/application-context.xml");

    Car obj1 = (Car) iocContainer.getBean("c1");
    Car obj2 = (Car) iocContainer.getBean("c1");
    Car obj3 = (Car) iocContainer.getBean("c1");

    System.out.println(obj1 == obj2);
    System.out.println(obj1 == obj3);

    // singleton으로 설정된 객체는
    // 오직 한 개만 생성된다.
    // getBean()을 여러 번 호출하더라도 같은 객체를 리턴한다.

    Car obj10 = (Car) iocContainer.getBean("c2");
    Car obj20 = (Car) iocContainer.getBean("c2");
    Car obj30 = (Car) iocContainer.getBean("c2");

    System.out.println(obj10 == obj20);
    System.out.println(obj10 == obj30);
  }

}


