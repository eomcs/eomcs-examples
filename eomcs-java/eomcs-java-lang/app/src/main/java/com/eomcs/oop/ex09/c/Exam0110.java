// 인터페이스 상속과 구현
package com.eomcs.oop.ex09.c;


public class Exam0110 {

  interface ProtocolA {
    void rule1();
  }

  // 인터페이스도 다른 인터페이스를 상속 받을 수 있다.
  // - ProtocolB 는 ProtocolA의 입장에서 서브 인터페이스이다.
  // - ProtocolA 는 ProtocolB의 입장에서 수퍼 인터페이스이다.
  interface ProtocolB extends ProtocolA {
    void rule2();
  }

  // 인터페이스를 구현할 때는
  // 수퍼 인터페이스의 메서드까지 모두 구현해야 한다.
  class ProtocolImpl implements ProtocolB {
    // ProtocolA 규칙 준수!
    @Override
    public void rule1() {System.out.println("rule1()");}

    // ProtocolB 규칙 준수!
    @Override
    public void rule2() {System.out.println("rule2()");}

    // 인터페이스와 상관 없이 메서드 추가
    public void m1() {System.out.println("m1()");}
  }

  void test() {

    ProtocolImpl obj = new ProtocolImpl();

    // 1) 클래스의 레퍼런스 사용,
    // - 그 클래스에 정의된 메서드 호출 가능
    // - 그 클래스의 상위 클래스에 정의된 메서드 호출 가능
    obj.rule1(); // OK
    obj.rule2(); // OK
    obj.m1(); // OK

    System.out.println("------------------------------------");

    // 2) 인터페이스의 레퍼런스 사용
    // - 인터페이스에 정의된 메서드 호출 가능 
    // - 상위 인터페이스에 정의된 메서드 호출 가능
    ProtocolB b = obj;
    b.rule2(); // OK --> ProtocolB.rule2() 
    b.rule1(); // OK --> ProtocolA.rule1()
    //    b.m1(); // 컴파일 오류 --> ProtocolImpl.m1() 

    System.out.println("-------------------------------");

    // 3) 수퍼 인터페이스의 레퍼런스 사용
    // - 인터페이스에 정의된 메서드 호출 가능 
    // - 상위 인터페이스에 정의된 메서드 호출 가능
    // - ProtocolImpl 클래스가 ProtocolB 의 규칙에 따라 제작되었다면
    //   결국 그 수퍼 인터페이스의 규칙도 준수하는 것이 된다.
    // - 따라서 수퍼 인터페이스 레퍼런스에 객체를 담을 수 있다.
    //   즉 수퍼 인터페이스 레퍼런스로 객체를 가리킬 수 있다.
    //
    ProtocolA a = obj;
    a.rule1(); // OK --> ProtocolA.rule1()
    //    a.rule2(); // 컴파일 오류 --> ProtocolB.rule2() 
    //    a.m1(); // 컴파일 오류 --> ProtocolImpl.m1() 
  }

  public static void main(String[] args) {
    new Exam0110().test();
  }
}







