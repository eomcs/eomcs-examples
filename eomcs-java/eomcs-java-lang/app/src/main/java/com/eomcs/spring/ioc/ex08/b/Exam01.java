// 의존 객체 주입 자동화하기 - 셋터에 @Autowired 애노테이션 붙이기
package com.eomcs.spring.ioc.ex08.b;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) {

    // [동작원리]
    // 1) 스프링 IoC 컨테이너는 객체를 만든다.
    // 2) 프로퍼티 값을 설정한다.
    // 3) 객체 생성 후 IoC 컨테이너에 등록된
    //    리스너(BeanPostProcessor)에게 통보한다.
    // 4) AutowiredAnnotationBeanPostProcessor 리스너가 있다면,
    //    @Autowired 애노테이션을 처리한다.
    //
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex08/b/application-context.xml");

    // Spring IoC 컨테이너에 들어 있는 객체 출력
    SpringUtils.printBeanList(iocContainer);

    // Car 객체를 꺼내 Engine 객체가 주입되었는지 확인해보자!
    System.out.println(iocContainer.getBean("c1"));

  }

}


