// 인터페이스 구현 - 규칙에 따라 클래스를 만든다.
package com.eomcs.oop.ex09.a2.after;

// 누가 도구를 만들든지 간에 항상 Spec 에 따라 만들기 때문에,
// - 이 도구를 사용하는 입장에서는 Spec에 맞춰 사용함으로써
//   프로그래밍에 일관성을 확보할 수 있다.
//
public class ToolC implements Spec {
  @Override
  public void m1() {
    System.out.println("ToolC.m1()");
  }
}








