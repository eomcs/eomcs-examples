// 오버라이딩(overriding) - 필드 오버라이딩
package com.eomcs.oop.ex06.c;

public class Exam0130 {

  static class A {
    String name;
    String tel;
    boolean working;

    void print() {
      System.out.println("A.print():");
      System.out.printf("  => this.name(%s)\n", this.name);
      System.out.printf("  => this.tel(%s)\n", this.tel);
      System.out.printf("  => this.working(%s)\n", this.working);

      // 인스턴스 필드를 찾는 때:
      // this => 메서드가 소속된 클래스의 필드부터 찾아 올라간다.
      // super => 메서드가 소속된 클래스의 수퍼 클래스 필드부터 찾아 올라간다.
    }
  }

  static class A4 extends A {
    // 필드 오버라이딩
    // - 필드 오버라이딩은 메서드와 달리 변수의 타입이 달라도 된다.
    //
    String working;

    void print2() {
      System.out.println("A4.print2():");
      System.out.printf("  => this.name(%s)\n", this.name);
      System.out.printf("  => this.tel(%s)\n", this.tel);
      System.out.printf("  => this.working(%s)\n", this.working);
    }
  }


  public static void main(String[] args) {
    A4 obj = new A4();
    obj.name = "홍길동"; // A의 name
    obj.tel = "1111-1111"; // A의 tel
    //    obj.working = true; // A4의 working : obj의 클래스에서 먼저 필드를 찾기 때문이다.
    obj.working = "취업";

    obj.print();
    // A의 print() 호출
    // - A4 가 오버라이딩 한 필드를 사용하지 않는다.
    // - 필드 오버라이딩은 그냥 새 필드를 추가한 것과 같다.
    // - 가능한 수퍼 클래스의 필드와 같은 이름을 가진 필드를 만들지 말라!
    // - A 클래스의 메서드에서는 A 설계도에 따라 만든 필드를 먼저 찾는다.

    System.out.println("---------------------");

    obj.print2();
    // - A4 클래스의 메서드에서는 A4 설계도에 따라 만든 필드를 먼저 찾는다.

  }
}
