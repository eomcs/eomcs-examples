// 프로퍼티 호출 - 의존 객체 주입할 때 즉시 객체 생성하기
package com.eomcs.spring.ioc.ex04.e;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.ex04.Car;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex04/e/application-context.xml");

    Car c1 = (Car) iocContainer.getBean("c1");
    Car c2 = (Car) iocContainer.getBean("c2");

    System.out.println(c1.getEngine() == c2.getEngine());

    // 프로퍼티 생성 중에 만드는 객체는 컨테이너에 따로 보관되지 않는다.
    // 따라서 그 객체를 선언할 때는 id를 지정할 필요가 없다.
    //    Engine e1 = (Engine) iocContainer.getBean("e1");    
  }

}


