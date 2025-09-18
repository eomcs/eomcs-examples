// 인터페이스 - 정의와 구현
package com.eomcs.oop.ex09.b;

// 1) 인터페이스 정의
interface MyInterface {
  // 규칙1) 인터페이스에 선언되는 모든 메서드는 public 이다.
  //   - 인터페이스에 정의하는 메서드는 호출 규칙이다.
  //   - 규칙은 공개되어야 한다.
  // 규칙2) 인터페이스에 선언되는 모든 메서드는 추상 메서드로 선언한다.
  //   - 인터페이스에 선언하는 메서드는 호출 규칙을 정의한 것이다.
  //   - 규칙은 클래스가 따라야 한다.
  //   - 그래서 인터페이스에 선언되는 모든 메서드는 몸체를 구현하지 않는다.
  public abstract void m1();

  // public 을 생략할 수 있다.
  abstract void m2(); // public 이 생략된 것이다. (default) 아니다!

  // abstract 를 생략할 수 있다.
  public void m3();

  // public, abstract 모두 생략할 수 있다.
  void m4();

  // => private, protected, (default)는 없다.
  //  private void m5(); // 컴파일 오류!
  //  protected void m6(); // 컴파일 오류!
  void m7(); // 이건 (default) 아니라, public 이 생략된 것이다.

}

// 2) 인터페이스 구현
abstract class MyInterfaceImpl implements MyInterface {
  @Override
  public void m1() {}

  // public 보다 접근 범위를 좁힐 수는 없다.
  @Override
  //  private void m2() {}  // 컴파일 오류!
  //  protected void m2() {} // 컴파일 오류!
  //  void m2() {} // 컴파일 오류!
  public void m2() {} // OK!

  // 인터페이스의 모든 메서드를 구현해야 한다.
  // 한 개라도 빠뜨린다면 concrete 클래스가 될 수 없다.
  // 추상 클래스로 선언해야 한다.
}

class MyInterfaceImpl2 implements MyInterface {
  @Override
  public void m1() {}

  @Override
  public void m2() {} // OK!

  @Override
  public void m3() {
  }

  @Override
  public void m4() {
  }

  @Override
  public void m7() {
  }
}

// 3) 인터페이스 사용
public class Exam01 {

  public static void main(String[] args) {
    // 인터페이스 레퍼런스 선언
    MyInterface obj = null;

    // 인터페이스의 구현체 생성
    // - 인터페이스를 구현한 클래스의 객체라면
    //   언제든 해당 인터페이스의 레퍼런스에 담을 수 있다.
    obj = new MyInterfaceImpl2();

    // 인터페이스는 규칙이기 때문에
    // 구체적인 구현 내용이 없다.
    // 그래서 인스턴스를 생성할 수 없다.
    //
    //    obj = new MyInterface(); // 컴파일 오류!
  }

}
