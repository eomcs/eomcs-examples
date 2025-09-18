// 디폴트 메서드의 다중 구현
package com.eomcs.oop.ex09.c;


public class Exam0310 {

  interface ProtocolA {
    void rule0(); // 추상 메서드인 경우 구현하지 않았기 때문에 다중 구현시 중복되더라도 문제가 되지 않는다.
    void rule1();

    // 구현한 메서드인 경우 다중 구현시 중복되면 문제가 발생하기 때문에 컴파일 오류!
    default void rule3() {
      System.out.println("**ProtocolA.rule3()**");
    }
  }

  interface ProtocolB {
    void rule0(); // 추상 메서드인 경우 구현하지 않았기 때문에 다중 구현시 중복되더라도 문제가 되지 않는다.
    void rule2();

    // 구현한 메서드인 경우 다중 구현시 중복되면 문제가 발생하기 때문에 컴파일 오류!
    default void rule3() {
      System.out.println("====> ProtocolB.rule3()");
    }
  }

  static class ProtocolImpl implements ProtocolA, ProtocolB {
    @Override
    public void rule0() {
      System.out.println("ProtocolImpl.rule1()");
    }

    // ProtocolA 규칙 준수!
    @Override
    public void rule1() {
      System.out.println("ProtocolImpl.rule1()");
    }

    // ProtocolB 규칙 준수!
    @Override
    public void rule2() {
      System.out.println("ProtocolImpl.rule2()");
    }

    // 인터페이스들에 같은 시그너처를 갖는 default 메서드가 있을 경우,
    // 어떤 메서드를 상속 받느냐에 따라 실행 결과가 달라지기 때문에
    // 다중 클래스 상속이 불가능 한 것처럼
    // 이 경우에도 다중 인터페이스 구현이 불가능 하다.
    //
    // 그러나, 다음과 같이 클래스에서 default 메서드를 오버라이딩을 한다면,
    // 어차피 인터페이스에서 구현한 default 메서드를 사용하지 않기 때문에
    // 이 경우에는 다중 구현이 가능한다.
    //
    //        @Override
    //        public void rule3() {
    //          System.out.println("ProtocolImpl.rule3()");
    //        }
  }

  public static void main(String[] args) {
    ProtocolImpl obj = new ProtocolImpl();
    obj.rule1();
    obj.rule2();
    obj.rule3();
  }
}







