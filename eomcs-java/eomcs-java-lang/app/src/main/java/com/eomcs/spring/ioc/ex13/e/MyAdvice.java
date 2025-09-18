// 특정 메서드 호출 전후에 실행되는 클래스
package com.eomcs.spring.ioc.ex13.e;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// 애노테이션을 이용하여 Advice 객체를 IoC 컨테이너에 등록하는 방법이다.
// => XML 설정을 사용할 때는 이 클래스의 객체 생성을 XML에서 했기 때문에
//    @Component 애노테이션을 붙이지 않았다.
// => 이제는 XML에서 이 클래스의 객체를 만들지 않기 때문에 이 애노테이션을 붙여야 한다.
//
// XML 설정 예:
//      <bean id="myAdvice" class="com.eomcs.spring.ioc.ex13.e.MyAdvice"/>
//
@Component

// 이 객체가 AOP Advice를 설정하는 객체임을 표시한다.
//
//   <aop:aspect id="aspect1" ref="myAdvice">
//
@Aspect
public class MyAdvice {

  //
  // XML 설정 예:
  //    <aop:before
  //        pointcut="execution(* com.eomcs.spring.ioc.ex13.e.X.*(..)) and args(a,b)"
  //        method="doBefore"/>
  //
  @Before("execution(* com.eomcs.spring.ioc.ex13.e.X.*(..)) and args(x,y)")
  public void doBefore(int x, int y) {
    System.out.printf("MyAdvice.doBefore(): %d, %d\n", x, y);
  }

  //XML 설정 예:
  //    <aop:after
  //        pointcut="execution(* com.eomcs.spring.ioc.ex13.e.X.*(..))"
  //        method="doAfter"/>
  //
  @After("execution(* com.eomcs.spring.ioc.ex13.e.X.*(..))")
  public void doAfter() {
    System.out.println("MyAdvice.doAfter()");
  }

  // XML 설정 예:
  //    <aop:after-returning
  //        pointcut="execution(* com.eomcs.spring.ioc.ex13.e.X.*(..))"
  //        method="doAfterReturning" returning="returnValue"/>
  //
  @AfterReturning(//
      pointcut = "execution(* com.eomcs.spring.ioc.ex13.e.X.*(..))",
      returning = "returnValue")
  public void doAfterReturning(Object returnValue) {
    System.out.printf("MyAdvice.doAfterReturning(): %d\n", returnValue);
  }

  // XML 설정 예:
  //    <aop:after-throwing
  //        pointcut="execution(* com.eomcs.spring.ioc.ex13.e.X.*(..))"
  //        method="doAfterThrowing" throwing="error"/>
  //
  @AfterThrowing(//
      pointcut = "execution(* com.eomcs.spring.ioc.ex13.e.X.*(..))", //
      throwing = "error")
  public void doAfterThrowing(Exception error) {
    System.out.printf("MyAdvice.doAfterThrowing(): %s\n", error.getMessage());
  }
}


