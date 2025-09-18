// 오버라이딩(overriding) - 문법 사용
package com.eomcs.oop.ex06.c;

public class Exam0120 {

  //오버라이딩(overrding)?
  //=> 부모로부터 상속 받은 메서드 중에서
  //   자신(서브클래스 입장에서)의 역할에 맞지 않는 메서드가 있다면,
  //   자신(서브클래스 입장에서)의 역할에 맞춰 상속받은 메서드를 재정의하여
  //   프로그래밍의 일관성을 확보하는 문법이다.
  //

  static class A {
    String name = "A";
    String tel = "A: 010-1111-1111";
    boolean working = true;

    void print() {
      System.out.println("A.print():");
      System.out.printf("  => this.name(%s)\n", this.name);
      System.out.printf("  => this.tel(%s)\n", this.tel);
      System.out.printf("  => this.working(%s)\n", this.working);
    }
  }

  static class A3 extends A {
    int age = 30;

    // 상속 받은 메서드가 서브 클래스의 역할에 맞지 않다면
    // 서브 클래스의 역할에 맞춰서 재정의 하라!
    // 재정의?
    // => 즉 부모의 메서드와 똑같은 메서드를 정의하는 것이다.
    // => 그래서 부모의 메서드를 덮어쓰는(override) 것이다.
    // => 이 메서드를 호출하는 개발자 입장에서는
    //    A 클래스와 같은 이름으로 된 메서드를 호출하기 때문에
    //    별다른 구분없이 사용할 수 있다.
    // => 부모 클래스의 메서드와 같은 시그너처(signature)를 갖고 
    //    리턴 타입도 같은 메서드를 만든다.
    // 
    // * 메서드 시그너처(method signature) = 함수 프로토타입(function prototype)
    //       메서드명, 파라미터 타입/개수/순서
    void print() {
      System.out.println("A3.print():");
      System.out.printf("  => this.name(%s)\n", this.name);
      System.out.printf("  => this.tel(%s)\n", this.tel);
      System.out.printf("  => this.working(%s)\n", this.working);
      System.out.printf("  => this.age(%s)\n", this.age);
    }
  }

  public static void main(String[] args) {
    A3 obj = new A3();
    obj.name = "홍길동";
    obj.age = 20;

    // A3 클래스에서 A 클래스의 print() 메서드를 재정의 했기 때문에
    // 다음 호출하는 메서드는 A3의 print() 메서드가 된다.
    obj.print(); // A3에서 재정의한 print() 호출

  }
}
