// 인터페이스 다중 구현과 메서드 중복
package com.eomcs.oop.ex09.c;


public class Exam0220 {

  interface ProtocolA {
    void rule0();
    void rule1();
  }

  interface ProtocolB {
    void rule0();
    void rule2();
  }

  // 다중 인터페이스를 구현할 때 중복된 메서드가 있다면,
  // - 메서드 시그너처(이름, 파라미터, 리턴타입)만 같으면
  //   다중 구현에 문제가 없다.
  // - 왜냐하면 구현 메서드가 인터페이스를 모두 만족시키기 때문이다.
  //
  class ProtocolImpl implements ProtocolA, ProtocolB {
    // ProtocolA 입장에서는 rule0() 규칙 준수!
    // ProtocolB 입장에서도 rule0() 규칙 준수!
    @Override
    public void rule0() {System.out.println("rule1()");}

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
    new Exam0220().test();
  }
}







