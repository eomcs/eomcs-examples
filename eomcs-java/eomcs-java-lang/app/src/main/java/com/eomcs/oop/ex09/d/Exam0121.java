// 인터페이스와 추상 클래스 : 추상 클래스 대신 default 문법 사용하기
package com.eomcs.oop.ex09.d;

public class Exam0121 {

  interface ProtocolA {
    // 추상 클래스를 사용할 바에는 인터페이스에서 미리 구현하는 방식도 많이 쓴다.
    // => 주의!
    //    - 추상 메서드는 구현을 강제하는 효과가 있다.
    //    - 그런데 default 메서드로 선언하면 구현을 강제할 수 없는 문제가 있다.
    default void rule1() {}
    default void rule2() {}
    default void rule3() {}
    default void rule4() {}
  }

  // 인터페이스에서 미리 모든 메서드를 구현했기 때문에
  // 추상 클래스에서 구현할 필요가 없다.
  //  abstract class AbstractProtocolA implements ProtocolA {
  //    @Override
  //    public void rule1() {}
  //
  //    @Override
  //    public void rule2() {}
  //
  //    @Override
  //    public void rule3() {}
  //
  //    @Override
  //    public void rule4() {}
  //  }

  // 인터페이스를 구현할 때 추상 클래스 없이 바로 구현할 수 있다.
  class ProtocolAImpl implements ProtocolA {
    // 인터페이스에서 모든 메서드를 미리 구현했기 때문에
    // 필요한 메서드만 오버라이딩 하면 된다.
    // 즉 추상 클래스를 상속 받아서 만드는 효과가 있다.
    //
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
    new Exam0121().test();
  }
}
