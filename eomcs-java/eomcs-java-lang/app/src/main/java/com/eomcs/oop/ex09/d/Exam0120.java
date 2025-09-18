// 인터페이스와 추상 클래스 : 추상 클래스의 도움 받기
package com.eomcs.oop.ex09.d;

public class Exam0120 {

  interface ProtocolA {
    void rule1();
    void rule2();
    void rule3();
    void rule4();
  }

  // 추상클래스에서 인터페이스의 규칙을 모두 미리 구현해 둔다.
  // 물론 최소 상태로 구현한다.
  abstract class AbstractProtocolA implements ProtocolA {
    @Override
    public void rule1() {}

    @Override
    public void rule2() {}

    @Override
    public void rule3() {}

    @Override
    public void rule4() {}
  }

  // 인터페이스를 준수하는 클래스를 만들어야 할 경우,
  // 직접 인터페이스를 구현하는 대신에
  // 다음과 같이 추상 클래스를 상속 받는다.
  // - 수퍼 클래스에서 인터페이스를 구현했다면,
  //   그 서브 클래스들도 인터페이스를 구현한 것이 된다.
  class ProtocolAImpl extends AbstractProtocolA {
    // 이 방식의 장점은 오버라이딩을 통해
    // 인터페이스 규칙 중 필요한 규칙만 구현할 수 있다.
    @Override
    public void rule2() {
      System.out.println("ProtocolAImpl.rule2()");
    }
  }

  void test() {
    ProtocolAImpl obj = new ProtocolAImpl();

    // 수퍼 클래스가 인터페이스를 구현했다면,
    // 그 서브 클래스는 자동으로 인터페이스를 구현한 것이 된다.
    // 증명!
    //
    ProtocolA a = obj;

    a.rule2();
  }

  public static void main(String[] args) {
    new Exam0120().test();
  }
}
