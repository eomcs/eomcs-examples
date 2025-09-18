package com.eomcs.oop.ex09.uc01.project4;

import com.eomcs.oop.ex09.uc01.Computer;

// 인터페이스의 'default 메서드' 문법의 단점
// - 메서드 구현을 강제할 수 없다.
// - 추상 메서드인 경우, 클래스가 반드시 해당 메서드를 구현해야 한다.
// - default 메서드는 이미 구현했기 때문에 클래스가 다시 구현하지 않아도 컴파일 할 때 문제가 발생하지 않는다.
// - 개발자가 새 기능 구현을 깜박 잊을 수 있다는 것이 문제다.
// 
public class NewComputer3 implements Computer {

  // 기존 규칙을 구현하고,
  @Override
  public void compute() {
    System.out.println("새 컴퓨터..");
  }

  // 새로 추가한 기능인 touch()를 구현하지 않아도 컴파일 오류가 발생하지 않는다.
  // 왜? 이미 구현했기 때문이다.
  // 이것이 default 메서드의 문제점이다.
  // 따라서 default 문법은 
  // 기존 인터페이스에 새 기능을 추가할 때만 가능한 사용하라.
  // 즉 기존에 그 인터페이스에 따라 이미 구현한 클래스들에 
  // 영향을 끼치지 않기 위한 용도로만 사용하라.
  // 새 인터페이스를 만들 때는 가능한 default 문법을 사용하지 말라!

}






