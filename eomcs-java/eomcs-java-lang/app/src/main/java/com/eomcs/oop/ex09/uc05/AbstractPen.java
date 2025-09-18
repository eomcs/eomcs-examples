// 추상 클래스 활용 - 서브 클래스를 위해 미리 인터페이스의 일부 메서드를 구현하는 역할
package com.eomcs.oop.ex09.uc05;


// 서브 클래스를 위해 인터페이스의 일부 메서드를 구현하는 용도로 추상 클래스를 많이 사용한다.
// 추상 클래스의 특성 상 필드와 구현 메서드를 가질 수 있어서 이런 용도로 많이 사용한다.
// => 인터페이스를 완전하게 구현하지 않았고,
//    직접 사용할 일도 없기 때문에 더더욱 추상 클래스로 선언해야 한다.
// => 그리고 이런 클래스의 이름이 보통 Abstract로 시작한다.
//
public abstract class AbstractPen implements Pen {

  // 서브 클래스에게 물려줄 필드
  boolean use;

  // 서브 클래스에서 동일하게 구현할 메서드라면 여기 수퍼 클래스에서 미리 구현하여 상속해준다.
  @Override
  public void on() {
    this.use = true;
  }

  @Override
  public void off() {
    this.use = false;
  }

  // write()는 어차피 서브 클래스에서 자신의 특성에 맞춰서 구현해야 하는 것이기 때문에
  // 수퍼 클래스에서는 구현하지 않는다.
}




