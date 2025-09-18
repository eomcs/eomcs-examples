// Wrapper 클래스 - primitive type 을 객체로 다루기
package com.eomcs.basic.ex02;

public class Exam0211 {
  public static void main(String[] args) {

    // Wrapper 클래스의 가장 큰 목적!
    // => primitive 값을 포함하여 모든 값을 쉽게 주고 받기 위함이다.

    // wapper 클래스가 없다면,
    // 다음과 같이 각 타입에 대한 메서드가 따로 있어야 한다.
    long l = 100L;
    double d = 3.14;
    boolean bool = true;

    m1(l);
    m1(d);
    m1(bool);

    // 이렇게 wrapper 클래스를 사용하면
    // 객체로 다룰 수 있다.
    Long obj1 = Long.valueOf(l);
    Double obj2 = Double.valueOf(d);
    Boolean obj3 = Boolean.valueOf(bool);

    m2(obj1);
    m2(obj2);
    m2(obj3);
  }

  // 만약에 Wrapper 클래스가 없다면 다음과 같이
  // 정수를 받는 메서드와 부동소수점을 받는 메서드,
  // 논리값을 받는 메서드를 따로따로 정의해야 한다.
  // => 이런 불편함을 없애기 위해 Wrapper 클래스를 만든 것이다.
  // => 즉 primitive type을 객체처럼 다룰 수 있도록 만든 문법이다.
  static void m1(long value) { // byte, short, int, long, char
    System.out.printf("long value=%s\n", value);
  }
  static void m1(double value) {// float, double
    System.out.printf("double value=%s\n", value);
  }
  static void m1(boolean value) {// boolean
    System.out.printf("boolean value=%s\n", value);
  }

  // wapper 클래스는 primitive type의 값을 객체로 다룰 수 있게 해준다.
  // primitive type에 상관없이 Object 타입의 파라미터로 값을 받을 수 있다.
  static void m2(Object value) { // 모든 객체를 받을 수 있다.
    System.out.printf("wrapper value=%s\n", value);
  }
}






