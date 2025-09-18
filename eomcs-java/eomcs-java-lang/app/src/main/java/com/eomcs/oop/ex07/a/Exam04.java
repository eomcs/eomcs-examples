// 추상 클래스와 추상 메서드 정리!
package com.eomcs.oop.ex07.a;

abstract class A4 {

  // 추상 클래스의 용도는
  // - 서브 클래스들이 가져야할 공통 변수나 메서드를 제공하는 것이다.
  // - 그래서 다음과 같이 일반 변수나 메서드를 정의할 수 있다.
  public static int value1 = 100;
  static void m1() {System.out.println("A4.m1() 호출됨!");}

  public String value2 = "Hello!";
  void m2() {System.out.println("A4.m2() 호출됨!");}

  // 추상 클래스는 추상 메서드도 가질 수 있다.
  // 의미?
  // - 서브클래스마다 구현이 다를 경우
  //   굳이 수퍼 클래스에서 정의할 필요가 없기 때문에
  //   완전히 정의하지 않은 추상 메서드로 남겨둔다.
  // - 서브클래스에게 메서드 구현을 강제하고 싶을 때도 추상 메서드를 활용한다.
  //   추상 메서드를 상속 받은 서브 클래스는 반드시 정의해야 하기 때문이다.
  //   서브 클래스가 상속 받은 추상 메서드를 구현하지 않는다면
  //   추상 메서드를 그냥 보유하기 때문에 일반 클래스가 될 수 없다.
  //   즉 추상 클래스가 되어야 한다.
  // - 의미
  //   "이 메서드는 수퍼 클래스에서 정의해봐야 소용없어.
  //    어차피 서브 클래스에서 재정의할 메서드야!"
  //   "이 메서드는 서브 클래스에서 반드시 정의해야 해.
  //    왜냐하면 수퍼 클래스에 정의한 다른 메서드가 이 메서드를 사용하기 때문이야!"
  //
  public abstract void m3();
}

class A4Sub extends A4 {
  @Override // 이 애노테이션은 빼도 된다. 참고, 메서드를 구현하는 것도 오버라이딩으로 본다.
  public void m3() {
    System.out.println("A4Sub.m3() 호출됨!");
  }
}

public class Exam04 {
  public static void main(String[] args) {
    A4 obj = new A4Sub();
    System.out.println(A4.value1);
    System.out.println(obj.value2);
    A4.m1();
    obj.m2();
    obj.m3();
  }
}
