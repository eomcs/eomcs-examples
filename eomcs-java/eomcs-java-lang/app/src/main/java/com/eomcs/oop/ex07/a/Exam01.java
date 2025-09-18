// 추상 클래스와 인스턴스
package com.eomcs.oop.ex07.a;

// 클래스 앞에 abstract를 붙인다.
// 추상 클래스의 목적
// - 추상 메서드가 있든 없든 상관없이 추상 클래스를 만들 수 있다.
// - 서브 클래스에게 공통 필드나 메서드를 상속해주는 것이 목적이다.
// - 직접 사용하지 않는다.
// - 여러 클래스를 같은 타입으로 묶기 위함이다.
// - 상속에서 generalization을 통해 수퍼 클래스를 정의하는 경우에
//   그 수퍼 클래스를 주로 추상 클래스로 만든다.
//
abstract class A {

}

public class Exam01 {

  public static void main(String[] args) {
    // 추상 클래스는 인스턴스를 생성할 수 없다.
    //    A obj1 = new A(); // 컴파일 오류!

    // 그러나 레퍼런스는 선언할 수 있다.
    A obj2 = null;
  }

}
