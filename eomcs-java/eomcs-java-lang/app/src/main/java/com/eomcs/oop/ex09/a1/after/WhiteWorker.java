// Worker 인터페이스를 이행하는 클래스 - 줄여서 "Worker 구현체"라 부른다.
package com.eomcs.oop.ex09.a1.after;

public class WhiteWorker implements Worker {
  // 인터페이스 구현체(인터페이스를 구현하는 클래스)는 
  // 반드시 인터페이스에 선언된 모든 메서드를 구현해야 한다.
  @Override
  public void execute() {
    System.out.println("사무직 노동자가 일을 합니다.");
  }
}
