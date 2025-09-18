// 특정 메서드 호출 전후에 실행되는 클래스
package com.eomcs.spring.ioc.ex13.f;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {

  // Pointcut을 미리 정의한다.
  //  => 메서드 선언부에 붙여야 한다.
  //  => 메서드의 파라미터는 없고, 구현을 비워둔다.
  //  => 이 메서드는 pointcut을 지정하는 용도로만 사용한다.
  //
  @Pointcut("execution(* com.eomcs.spring.ioc.ex13.f.X.*(..))")
  public void calculatorOperation() {}

  @Before("calculatorOperation() and args(x,y)")
  public void doBefore(int x, int y) {
    System.out.printf("MyAdvice.doBefore(): %d, %d\n", x, y);
  }

  @After("calculatorOperation()")
  public void doAfter() {
    System.out.println("MyAdvice.doAfter()");
  }

  @AfterReturning(
      pointcut = "calculatorOperation()",
      returning = "returnValue")
  public void doAfterReturning(Object returnValue) {
    System.out.printf("MyAdvice.doAfterReturning(): %d\n", returnValue);
  }

  @AfterThrowing(
      pointcut = "calculatorOperation()",
      throwing = "error")
  public void doAfterThrowing(Exception error) {
    System.out.printf("MyAdvice.doAfterThrowing(): %s\n", error.getMessage());
  }
}


