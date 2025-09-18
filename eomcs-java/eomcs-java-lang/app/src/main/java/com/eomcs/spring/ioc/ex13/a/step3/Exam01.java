// AOP(Aspect-Oriented Programming) - AOP 필터 적용 후 : X.m1()의 실행 시간 측정하기
package com.eomcs.spring.ioc.ex13.a.step3;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

public class Exam01 {

  public static void main(String[] args) {
    // 기존 코드를 손대지 않고 기능을 추가하기 
    // => m1() 메서드 안에 삽입할 코드를 별도의 클래스로 정의한다.
    // => 해당 기능을 m1() 메서드 전후, 언제 삽입할 것인지 설정한다.
    //

    ClassPathXmlApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex13/a/step3/application-context.xml");

    SpringUtils.printBeanList(iocContainer);

    X x = iocContainer.getBean(X.class);
    x.m1();
  }

}

// AOP 기술을 이용한 필터 적용 방법
// 1) AOP 관련 의존 라이브러리를 프로젝트에 적용한다.
//   - search.maven.org 에서 "aspectjweaver"로 검색한다.
//   - build.gradle 에 등록
//   - "gradle eclipse" 실행
//   - 프로젝트 리프래시.
// 2) 필터 클래스를 정의한다.
//   - MyAdvice 클래스 추가
// 3) XML 또는 자바 코드로 필터를 설정한다.
//   - application-context.xml 변경