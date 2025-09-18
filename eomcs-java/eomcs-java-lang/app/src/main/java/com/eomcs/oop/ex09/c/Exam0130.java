// 인터페이스 다중 상속과 메서드 중복
package com.eomcs.oop.ex09.c;


public class Exam0130 {

  interface ProtocolA {
    void rule0();
    void rule1();
  }

  interface ProtocolB {
    void rule0();
    void rule2();
  }

  // ProtoclA와 ProtocolB에 같은 이름의 메서드가 있더라도
  // 메서드 시그너처(이름, 파라미터, 리턴타입)가 같다면
  // 다중 상속이 가능하다.
  // - 클래스와 달리 메서드를 구현하기 전이라서 충돌날 일이 없다.
  //
  interface ProtocolC extends ProtocolA, ProtocolB {
    void rule3();
  }

  // 인터페이스를 구현할 때는
  // 수퍼 인터페이스의 메서드까지 모두 구현해야 한다.
  class ProtocolImpl implements ProtocolC {

    // ProtocolA, ProtocolB 규칙 동시 준수!
    @Override
    public void rule0() {System.out.println("rule1()");}

    // ProtocolA 규칙 준수!
    @Override
    public void rule1() {System.out.println("rule1()");}

    // ProtocolB 규칙 준수!
    @Override
    public void rule2() {System.out.println("rule2()");}

    // ProtocolC 규칙 준수!
    @Override
    public void rule3() {System.out.println("rule3()");}
  }

  void test() {

    ProtocolImpl obj = new ProtocolImpl();

    // 1) 인터페이스 레퍼런스로 구현체의 주소 받기
    ProtocolC c = obj;
    ProtocolB b = obj;
    ProtocolA a = obj;

    // 2) 메서드 호출
    // - 해당 인터페이스의 규칙에 따라서만 호출할 수 있다.

    c.rule0(); // OK
    c.rule1(); // OK
    c.rule2(); // OK
    c.rule3(); // OK
    System.out.println("-------------------------------");


    b.rule0(); // OK
    //    b.rule1(); // 컴파일 오류!
    b.rule2(); // OK
    //    b.rule3(); // 컴파일 오류!
    System.out.println("-------------------------------");

    a.rule0(); // OK
    a.rule1(); // OK
    //    a.rule2(); // 컴파일 오류!
    //    a.rule3(); // 컴파일 오류!
    System.out.println("-------------------------------");
  }

  public static void main(String[] args) {
    new Exam0130().test();
  }
}







