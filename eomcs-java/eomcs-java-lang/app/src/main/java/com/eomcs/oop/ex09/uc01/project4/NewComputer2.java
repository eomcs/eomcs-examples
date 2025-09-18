package com.eomcs.oop.ex09.uc01.project4;

import com.eomcs.oop.ex09.uc01.Computer;

// 4번째 프로젝트에서 터치 기능이 들어간 새 컴퓨터를 만들기로 했다.
// 방법2)
//   => 방법1처럼 기존 규칙을 상속 받아서 새 규칙(Computer2)을 만들면,
//      계속 규칙이 늘어나 관리하기가 어려운 문제가 있다.
//      그리고 규칙에 따라 구분하도록 코딩해야 하기 때문에 프로그래밍 하기가 불편한다.
//   => 그렇다고 기존 인터페이스에 새 메서드를 추가하면 
//      기존에 구현한 클래스는 모두 오류가 발생할 것이다.
//      왜? 
//      규칙이 달라졌기 때문에 클래스 구현 오류가 발생하는 것이다.
//      클래스는 항상 인터페이스의 모든 메서드를 구현해야 하기 때문이다.
//   => 해결채?
//      default 문법을 이용하여  기존 인터페이스에 새 메서드를 추가한다면 이런 문제가 사라진다.
//      기존 클래스 입장에서는 구현된 메서드를 상속 받기 때문에 오류가 발생하지 않는다.
// 
public class NewComputer2 implements Computer {

  // 기존 규칙을 구현하고,
  @Override
  public void compute() {
    System.out.println("새 컴퓨터..");
  }

  // 새로 추가한 규칙도 구현한다.
  @Override
  public void touch() {
    System.out.println("오호라.. 터치가 되네. 이거 서피스 프로인가?");
  }

}






