// 특정 메서드 호출 전후에 실행되는 클래스
package com.eomcs.spring.ioc.ex13.a.step3;

public class MyAdvice {
  long startTime;

  // m1() 메서드를 실행하기 전에 수행할 기능을 정의한다.
  public void advice1() {
    System.out.println("MyAdvice.advice1()");

    startTime = System.currentTimeMillis();
    System.out.println("시간 측정 시작!");
  }

  // m1() 메서드를 실행한 후에 수행할 기능을 정의한다.
  public void advice2() {
    System.out.println("MyAdvice.advice2()");

    long endTime = System.currentTimeMillis();
    System.out.println("시간 측정 종료!");
    System.out.println("걸린 시간: " + (endTime - startTime));
  }
}
