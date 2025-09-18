// 메서드의 시그너처가 다르다면 당연히 다중 구현 가능
package com.eomcs.oop.ex09.c;


public class Exam0231 {

  interface ProtocolA {
    void rule0(int a);
    void rule1();
  }

  interface ProtocolB {
    int rule0();
    void rule2();
  }

  class ProtocolImpl implements ProtocolA, ProtocolB {
    // ProtocolA 입장에서는 rule0() 규칙 준수!
    // ProtocolB 입장에서는 rule0() 규칙을 준수하지 못했다.
    // - 리턴 타입이 다르다.
    @Override
    public void rule0(int a) {}

    // ProtocolB 입장에서는 rule0() 규칙 준수!
    // ProtocolA 입장에서는 rule0() 규칙을 준수하지 못했다.
    // - 리턴 타입이 다르다.
    @Override
    public int rule0() {return 0;}

    // 두 메서드를 모두 정의하면 되지 않을까?
    // - 메서드 오버로딩 문법 상 리턴 타입만 다른 메서드를
    //   동시에 정의할 수 없다.

    // ProtocolA 규칙 준수!
    @Override
    public void rule1() {}

    // ProtocolB 규칙 준수!
    @Override
    public void rule2() {}
  }

  public static void main(String[] args) {
  }
}







