// AOP(Aspect-Oriented Programming) - AOP 필터를 적용하는 방법
package com.eomcs.spring.ioc.ex13.b;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;

// AOP(Aspect-Oriented Programming, 관점 지향 프로그래밍)?
// => 기존의 코드를 손대지 않고 특정 기능을 삽입하는 기술
// => 메서드 호출 앞뒤로 코드를 삽입할 수 있다.
// => 일종의 필터 기술이다.
// => 패턴을 이용하여 여러 클래스나 메서드에 집단적으로 기능을 추가하거나 뺄 수 있다.
//
// AOP 구동 원리
// => AOP는 "proxy 디자인 패턴"을 사용한다.
// => 즉 AOP를 설정하면, 
//    Spring IoC 컨테이너는 point-cut에 지정된 클래스에 대해 Proxy 클래스를 생성한다.
//    그리고 그 Proxy 객체를 IoC 컨테이너에 저장한다.
// => IoC 컨테이너에서 point-cut 에 지정한 객체를 꺼내면 
//    원래의 객체 대신 프록시 객체가 리턴된다.
// => 프록시 객체에 대해 메서드를 호출하면, 
//    AOP에 설정한 대로 Advice 메서드를 호출한 후 원래의 메서드를 호출한다.

// AOP 기술 사용 방법:
// 1) AOP 관련 의존 라이브러리 프로젝트에 적용한다.
// - search.maven.org 에서 "aspectjweaver"로 검색한다.
// - build.gradle 에 등록
// - '$ gradle eclipse' 실행
// - 이클립스 프로젝트 리프래시.
// 2) 필터 역할을 수행할 클래스를 정의한다.
// 3) 스프링 설정(XML 또는 자바 코드)으로 필터를 적용한다.
//
// AOP 용어:
// [Advice]
// => 메서드(join point) 호출 앞뒤에 삽입될 코드를 담고 있는 필터 객체이다.
// 예) MyAdvice 클래스
//
// [Join Point]
// => Advice가 삽입될 메서드이다.
// 예) X.m1() 메서드
//
// [Pointcut]
// => Advice를 삽입할 위치 정보이다.
// => 즉 Join Point를 가리키는 패턴 정보이다.
// 예) execution(* com.eomcs.spring.ioc.ex13.b.*.*(..))
//
// [Target Object]
// => Advice를 삽입할 대상 객체이다.
// 예) X 클래스
//
// [Aspect]
// => 어느 pointcut 에 어떤 advice를 삽입할 것인지 가리키는 정보이다.
// 예) 설정 정보
//
public class Exam01 {

  public static void main(String[] args) {
    ClassPathXmlApplicationContext iocContainer = new ClassPathXmlApplicationContext(
        "com/eomcs/spring/ioc/ex13/b/application-context-01.xml");

    SpringUtils.printBeanList(iocContainer);

    X x = iocContainer.getBean(X.class);
    x.m1();
    System.out.println("-----------------------");

    x.m2();
    System.out.println("-----------------------");

    x.m3();
  }

}


