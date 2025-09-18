// 오버라이딩(overriding) - 오버라이딩 실수의 예
package com.eomcs.oop.ex06.c;

public class Exam0210 {

  static class B {
    void m(int a) {
      System.out.println("B의 m()");
    }
  }

  static class B2 extends B {
    // 오버라이딩 문법
    // => 메서드명, 파라미터 형식, 리턴 타입이 같아야 한다.
    // => 파라미터의 이름은 달라도 된다. 상관없다.
    //
    void m(float x) {
      // 그런데 이 메서드는 실수로 파라미터 타입을 float으로 했다.
      // 이것은 오버라이딩이 아니라 오버로딩이 된 것이다.
      // 즉 float 파라미터를 받는 m() 메서드가 추가된 것이다.
      // 그런데 개발자는 오버라이딩(재정의)을 했다고 착각할 수 있다.
      System.out.println("B2의 m()");
    }
  }
  public static void main(String[] args) {
    // 1) 오버라이딩을 잘못한 예:
    B2 obj = new B2();

    // B2에서 B의 m() 메서드를 오버라이딩(재정의) 했다고 착각하고
    // 메서드를 사용할 수 있다.
    // => 그런데 B2 클래스를 가보면 m()의 파라미터가 float이다.
    //    즉 오버라이딩을 한 게 아니라 오버로딩을 한 것이 된다.
    obj.m(100); // B의 m(int) 메서드를 호출
    obj.m(3.14f); // B2의 m(float) 메서드를 호출

    // 해결책?
    // => 개발자들의 이런 실수가 많이 발생해서 자바에서는
    //    오버라이딩 여부를 검사하는 특별한 문법을 추가하였다.
  }
}








