// 추상 클래스를 상속 받아서 간접적으로 인터페이스를 구현하기
package com.eomcs.oop.ex09.uc05;

// 인터페이스를 직접 구현하면 인터페이스에 선언된 모든 메서드를 정의해야 하지만,
// 다음과 같이 미리 인터페이스의 일부를 구현한 추상 클래스를 상속 받으면
// 좀 더 쉽게 인터페이스를 구현할 수 있다.
//
public class BallPen153 extends AbstractPen {
  // Pen 인터페이스의 기본 규칙은 이미 AbsractPen에서 구현했기 때문에
  // 추상 클래스에서 구현하지 않은 메서드만 서브 클래스에서 구현하면 된다.
  // => MyPen 클래스와 비교해 보라!
  @Override
  public void write(String text) {
    if (!this.use)
      return;

    System.out.println("모나미 153 : " + text);
  }
}
