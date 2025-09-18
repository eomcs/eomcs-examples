// 인터페이스 다중 구현이 불가한 경우!
package com.eomcs.oop.ex09.c;


public class Exam0230 {

  interface ProtocolA {
    void rule0();
    void rule1();
  }

  interface ProtocolB {
    int rule0();
    void rule2();
  }

  // 다중 인터페이스를 구현이 불가한 경우,
  // - 메서드 이름만 같고
  //   메서드 시그너처의 다른 부분(파라미터, 리턴타입)이 다른 경우.
  // - 두 인터페이스를 모두 만족시키지 못하기 때문이다.
  //
  //  class ProtocolImpl implements ProtocolA, ProtocolB {
  //    // ProtocolA 입장에서는 rule0() 규칙 준수!
  //    // ProtocolB 입장에서는 rule0() 규칙을 준수하지 못했다.
  //    // - 리턴 타입이 다르다.
  //    @Override
  //    public void rule0() {}
  //
  //    // ProtocolB 입장에서는 rule0() 규칙 준수!
  //    // ProtocolA 입장에서는 rule0() 규칙을 준수하지 못했다.
  //    // - 리턴 타입이 다르다.
  //    @Override
  //    public int rule0() {return 0;}
  //
  //    // 두 메서드를 모두 정의하면 되지 않을까?
  //    // - 메서드 오버로딩 문법 상 리턴 타입만 다른 메서드를
  //    //   동시에 정의할 수 없다.
  //
  //    // ProtocolA 규칙 준수!
  //    @Override
  //    public void rule1() {}
  //
  //    // ProtocolB 규칙 준수!
  //    @Override
  //    public void rule2() {}
  //  }

  public static void main(String[] args) {
  }
}







