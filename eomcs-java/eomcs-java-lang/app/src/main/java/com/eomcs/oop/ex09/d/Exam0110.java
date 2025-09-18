// 인터페이스와 추상 클래스 : 인터페이스 직접 구현
package com.eomcs.oop.ex09.d;


public class Exam0110 {

  interface ProtocolA {
    void rule1();
    void rule2();
    void rule3();
    void rule4();
  }

  // 인터페이스를 준수한다는 것은
  // 인터페이스의 모든 규칙을 구현해야 함을 의미한다.
  class ProtocolAImpl implements ProtocolA {
    @Override
    public void rule1() {}

    @Override
    public void rule2() {}

    @Override
    public void rule3() {}

    @Override
    public void rule4() {}
  }
}
