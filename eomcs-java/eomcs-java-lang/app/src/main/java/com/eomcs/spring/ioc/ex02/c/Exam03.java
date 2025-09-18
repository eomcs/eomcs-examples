// 객체 생성 - 빈 생성 정책 : prototype 객체
package com.eomcs.spring.ioc.ex02.c;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.ex02.Car;

public class Exam03 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex02/c/application-context.xml");

    System.out.println("--------------------------");
    Car obj1 = (Car) iocContainer.getBean("c3");
    System.out.println("--------------------------");
    Car obj2 = (Car) iocContainer.getBean("c3");
    System.out.println("--------------------------");
    Car obj3 = (Car) iocContainer.getBean("c3");
    System.out.println("--------------------------");

    System.out.println(obj1 == obj2);
    System.out.println(obj1 == obj3);
    System.out.println(obj2 == obj3);

    // prototype 객체
    // => getBean()을 호출할 때 마다 새 객체를 만들어 리턴한다.
    // => 특별한 경우가 아니면 이 방식을 사용하지 않는다.
    // => 왜? 객체가 계속 생성되기 때문에 가비지가 많이 발생할 수 있다.
    // => 그래서 IoC 컨테이너는 기본으로 singleton 방식을 사용한다.
  }

}


