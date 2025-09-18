// 특정 메서드 호출 전후에 실행되는 클래스
package com.eomcs.spring.ioc.ex13.b;

public class MyAdvice2 {
  // 삽입될 메서드를 정의한다.
  public void okAdvice() {
    System.out.println("MyAdvice2.okAdvice()");
  }

  public void hahaAdvice() {
    System.out.println("MyAdvice2.hahaAdvice()");
  }
}
