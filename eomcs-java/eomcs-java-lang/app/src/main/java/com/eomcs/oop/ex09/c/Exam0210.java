// 인터페이스 다중 구현
package com.eomcs.oop.ex09.c;


public class Exam0210 {

  interface ProtocolA {
    void rule1();
  }

  interface ProtocolB {
    void rule2();
  }

  // 인터페이스는 단지 규칙이기 때문에
  // (즉 아직 구현하지 않은 메서드이기 떄문에)
  // 다중 구현이 가능하다.
  class ProtocolImpl implements ProtocolA, ProtocolB {
    // ProtocolA 규칙 준수!
    @Override
    public void rule1() {System.out.println("rule1()");}

    // ProtocolB 규칙 준수!
    @Override
    public void rule2() {System.out.println("rule2()");}
  }

  void test() {

    ProtocolImpl obj = new ProtocolImpl();

    // 1) 인터페이스 레퍼런스로 구현체의 주소 받기
    ProtocolB b = obj;
    ProtocolA a = obj;

    // 2) 메서드 호출
    // - 해당 인터페이스의 규칙에 따라서만 호출할 수 있다.
    b.rule2(); // OK
    //    b.rule1(); // 컴파일 오류!
    System.out.println("-------------------------------");

    a.rule1(); // OK!
    //    a.rule2(); // 컴파일 오류!
  }

  public static void main(String[] args) {
    new Exam0210().test();
  }
}







