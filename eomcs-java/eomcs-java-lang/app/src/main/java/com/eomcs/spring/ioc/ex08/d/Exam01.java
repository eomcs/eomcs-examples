// 의존 객체 주입 자동화하기 - 인스턴스 필드에 @Autowired 애노테이션 붙이기
package com.eomcs.spring.ioc.ex08.d;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) throws Exception {
    // @Autowired 애노테이션을 필드(인스턴스 변수)에 붙여도 된다.
    // => 그러면 의존 객체를 직접 변수에 주입한다.
    // => 셋터를 호출하지 않는다. 즉 셋터가 없어도 된다.
    // => 인스턴스 변수에 직접 의존 객체를 주입한다는 것은
    // 캡슐화(즉 외부에서 직접 인스턴스 변수에 접근하는 것을 막는 기법)를
    // 위배하는 측면이 있기 때문에
    // 이 방식은 "객체지향을 파괴하는 방식"이라는 비난을 받는다.
    //
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex08/d/application-context.xml");

    // Spring IoC 컨테이너에 들어 있는 객체 출력
    SpringUtils.printBeanList(iocContainer);

    // Car 객체를 꺼내 Engine 객체가 주입되었는지 확인해보자!
    System.out.println(iocContainer.getBean("c1"));

  }

}


