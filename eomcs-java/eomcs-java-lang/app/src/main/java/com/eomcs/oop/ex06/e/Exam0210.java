// final 사용법: 오버라이딩 불가!
package com.eomcs.oop.ex06.e;

class B {
  // 메서드에 final 을 붙이면 서브 클래스에서 오버라이딩 할 수 없다.
  // - 서브 클래스에서 변경하면 안되는 메서드인 경우에 사용한다.
  // - 예)
  //   - 보안에 관련된 일을 하는 메서드
  //   - 템플릿 메서드 디자인 패턴에서처럼
  //     전체적인 작업 흐름을 정의한 메서드의 경우
  //     서브 클래스의 오버라이딩을 막는 것이 좋다.
  //
  final void m1() {
    System.out.println("B.m() 호출!");
  }

  void m2() {

  }
}
public class Exam0210 extends B {

  // final 메서드는 오버라이딩 불가!
  //
  //  @Override
  //  void m1() {
  //
  //  }

  // 일반 메서드는 오버라이딩 가능!
  @Override
  void m2() {

  }

}
