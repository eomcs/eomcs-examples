// 인터페이스 - private 메서드
package com.eomcs.oop.ex09.b;

interface MyInterface4 {

  void m1();

  default void m2() {
    System.out.println("MyInterface4.m2()");
    x();
  };

  default void m3() {
    System.out.println("MyInterface4.m3()");
    x();
  };

  // 인터페이스 내부에서 사용할 메서드라면
  // private 접근 범위를 갖는
  // 구현 메서드를 정의할 수 있다.
  // 언제?
  // - 해당 기능을 m2()와 m3() 처럼 여러 메서드에서 사용해야 할 경우
  //   그 공통 코드를 다음과 같이 private 구현 메서드로 정의하면 될 것이다.
  //
  private void x() {
    System.out.println("MyInterface4.x()");
  }
}

// 2) 인터페이스 구현
class MyInterface4Impl implements MyInterface4 {
  @Override
  public void m1() {

    //    m2(); // 현재 클래스의 m2() 메서드

    // 인터페이스에 선언된 오버라이딩 전의 default 메서드를 호출하고 싶다면,
    //    super.m2(); // 수퍼 클래스(Object)에서 m2()를 찾아 올라간다. Object에는 m2()가 없기 때문에 컴파일 오류!
    //    MyInterface4.m2(); // m2()는 인스턴스(non-static) 메서드이기 때문에 인터페이스 이름으로 직접 호출 불가!
    MyInterface4.super.m2();

    System.out.println("MyInterface4Impl.m1()");
  }

  @Override
  public void m2() {
    System.out.println("MyInterface4Impl.m2()");
  }
}

public class Exam04 {

  public static void main(String[] args) {
    MyInterface4 obj = new MyInterface4Impl();

    obj.m1();
    System.out.println("-----------------------------");

    obj.m2();
    System.out.println("-----------------------------");

    obj.m3();
    System.out.println("-----------------------------");

    // 인터페이스에 정의된 private 메서드는 호출할 수 없다.
    //    obj.x(); // 컴파일 오류!

  }

}
