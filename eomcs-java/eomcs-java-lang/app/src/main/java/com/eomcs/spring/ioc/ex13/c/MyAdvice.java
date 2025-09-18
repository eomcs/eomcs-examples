// 특정 메서드 호출 전후에 실행되는 클래스
package com.eomcs.spring.ioc.ex13.c;

public class MyAdvice {

  public void doBefore() {
    System.out.println("MyAdvice.doBefore()");
  }

  public void doAfter() {
    System.out.println("MyAdvice.doAfter()");
  }

  public void doAfterReturning() {
    System.out.println("MyAdvice.doAfterReturning()");
  }

  public void doAfterThrowing() {
    System.out.println("MyAdvice.doAfterThrowing()");
  }

  public void doAround() {
    System.out.println("MyAdvice.doAround()");
  }
}










