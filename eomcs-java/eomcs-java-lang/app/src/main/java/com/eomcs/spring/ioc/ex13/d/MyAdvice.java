// 특정 메서드 호출 전후에 실행되는 클래스
package com.eomcs.spring.ioc.ex13.d;

public class MyAdvice {

  // 타겟 객체의 메서드를 호출하기 전에 
  // 그 메서드가 받을 파라미터를 먼저 받을 수 있다.
  //
  // 1) advice에 파라미터 값을 받을 변수를 선언하라.
  //
  //      void doBefore(int x, int y)
  // 
  // 2) XML 설정에서 파라미터 값을 받겠다고 지정하라.
  //
  //      <aop:before 
  //            pointcut="execution(* com.eomcs.spring.ioc.ex13.d.X.*(..)) and args(y,x)" 
  //            method="doBefore"/>
  // 
  //    - 파라미터 값은 설정에서 지정한 변수에 순선대로 전달 된다.
  //    - 첫 번째 파라미터 값은 y 변수에서 받을 것이고
  //    - 두 번째 파라미터 값은 x 변수에서 받을 것이다.
  //    - 따라서 설정에서 변수를 지정할 때 변수의 이름이 중요하다.
  //
  public void doBefore(int x, int y) {
    System.out.printf("MyAdvice.doBefore(): %d, %d\n", x, y);
  }

  // 타겟 객체의 메서드를 호출한 후 리턴 값을 받을 수 있다.
  //
  // 1) advice에 리턴 값을 받을 변수를 선언하라.
  // 
  //      void doAfterReturning(Object returnValue)
  // 
  // 2) XML 설정에서 리턴 값을 받겠다고 지정하라.
  //  
  //      <aop:after-returning 
  //          pointcut="execution(* com.eomcs.spring.ioc.ex13.d.X.*(..))" 
  //          method="doAfterReturning"
  //          returning="returnValue"/>
  //  
  //    - 타겟 메서드의 리턴 값은 설정 파일에 정의한 대로 
  //      'returnValue' 라는 이름의 변수에서 받을 것이다.
  // 
  public void doAfterReturning(Object returnValue) {
    System.out.printf("MyAdvice.doAfterReturning(): %d\n", returnValue);
  }

  // 타겟 객체의 메서드를 호출할 때 예외가 발생했다면 그 예외 객체를 받을 수 있다.
  //
  // 1) advice에 예외 객체를 받을 변수를 선언하라.
  //
  //      void doAfterThrowing(Exception error) 
  // 
  // 2) XML 설정에서 예외 객체를 받겠다고 지정하라.
  // 
  //      <aop:after-throwing 
  //          pointcut="execution(* com.eomcs.spring.ioc.ex13.d.X.*(..))" 
  //          method="doAfterThrowing"
  //          throwing="error"/>
  //
  //    - 타겟 메서드의 예외 객체는 설정 파일에 정의한 대로 
  //      'error' 라는 이름의 변수에서 받을 것이다.
  //
  public void doAfterThrowing(Exception error) {
    System.out.printf("MyAdvice.doAfterThrowing(): %s\n", error.getMessage());
  }
}










