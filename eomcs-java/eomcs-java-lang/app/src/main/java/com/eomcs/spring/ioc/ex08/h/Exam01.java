// 의존 객체 주입 자동화하기 - @Resource = @Autowired + @Qualifier
package com.eomcs.spring.ioc.ex08.h;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) {

    // @Autowired와 @Qualifier를 묶어서 사용하는 대신에
    // 자바 언어 확장 명세서 'JSR-250'에 정의된 @Resource를 사용할 수 있다.
    // 단 이 애노테이션을 사용하려면
    // 이 애노테이션이 정의된 라이브러리를 별도로 다운로드 받아야 한다.
    // 이 애노테이션은 자바 확장 라이브러리에 포함되어 있다.
    // search.maven.org 에서 'javax.annotation' 으로 검색해 보라.
    //
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex08/h/application-context.xml");

    // Spring IoC 컨테이너에 들어 있는 객체 출력
    SpringUtils.printBeanList(iocContainer);

    // Car 객체를 꺼내 Engine 객체가 주입되었는지 확인해보자!
    System.out.println(iocContainer.getBean("c1"));

  }

}


