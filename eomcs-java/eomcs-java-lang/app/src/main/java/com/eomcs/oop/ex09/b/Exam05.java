// 인터페이스 - static 메서드
package com.eomcs.oop.ex09.b;

interface MyInterface5 {
  // 인터페이스도 클래스처럼 static 메서드를 정의할 수 있다.
  // => 접근 범위는 기본이 public 이다. 다른 접근 범위를 가질 수 없다.
  // => public 을 생략할 수 있다.
  //
  // 다음 메서드는 public 이 생략된거지, (package-private) 접근 범위가 아니다.
  static void m1() {
    System.out.println("MyInterface5.m1()");
  }

  //Java9 부터는 private 접근 범위도 가능하다.
  private static void x() {
    System.out.println("MyInterface5.m1()");
  }
}

class Parent {
  static void m2() {
    System.out.println("Parent.m2()");
  }
}

class MyInterface5Impl extends Parent implements MyInterface5 {

}

public class Exam05 {
  public static void main(String[] args) {
    // 인터페이스의 스태틱 메서드 호출하기
    MyInterface5.m1();

    //    MyInterface5.x(); // private static 멤버는 호출 불가!

    // 클래스의 스태틱 메서드 호출하기
    Parent.m2();

    // 그러나, 인터페이스에 정의된 static 메서드는,
    // => 인터페이스를 구현한 클래스를 통해 호출할 수 없다.
    //    MyInterface5Impl.m1(); // 컴파일 오류!

    // 수퍼 클래스에 정의된 static 메서드는,
    // => 다음과 같이 서브 클래스를 통해 호출할 수 있다.
    MyInterface5Impl.m2();


    MyInterface5Impl obj = new MyInterface5Impl();

    // => 구현체의 레퍼런스를 통해서는 인터페이스의 스태틱 메서드를 호출할 수 없다.
    //    obj.m1(); // 컴파일 오류!

    // => 자식 클래스의 레퍼런스를 통해서는 수퍼 클래스의 스태틱 메서드를 호출할 수 있다.
    // => 물론 스태틱 메서드는 가능한 그 메서드가 선언된 클래스 이름으로 호출하는 것이 좋다.
    obj.m2();


  }

}
