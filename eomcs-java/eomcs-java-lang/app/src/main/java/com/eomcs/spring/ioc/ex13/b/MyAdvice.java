// 특정 메서드 호출 전후에 실행되는 클래스
package com.eomcs.spring.ioc.ex13.b;

public class MyAdvice {
  // 삽입될 메서드를 정의한다.
  public void advice1() {
    System.out.println("MyAdvice.advice1()");
  }

  public void advice2() {
    System.out.println("MyAdvice.advice2()");
  }
}
