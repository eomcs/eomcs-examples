// AOP(Aspect-Oriented Programming) - AOP 필터 적용 전 : X.m1()의 실행 시간 측정하기
package com.eomcs.spring.ioc.ex13.a.step2;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) {
    // 기존 메서드에 기능을 추가하는 고전적인 방식
    // => m1() 메서드 안에 직접 코드를 추가한다.
    // => 또는 외부 클래스에 추가할 코드를 작성해 놓고 m1()에서 호출한다.
    // => 어떤 방법을 사용하든 m1()에 기능을 추가하려면 m1()을 편집해야 한다.
    //

    ClassPathXmlApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex13/a/step2/application-context.xml");

    SpringUtils.printBeanList(iocContainer);

    X x = iocContainer.getBean(X.class);
    x.m1();
  }

}


