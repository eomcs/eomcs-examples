// 오버라이딩(overriding) - 필드 오버라이딩 + super
package com.eomcs.oop.ex06.c;

public class Exam0140 {

  static class A {
    String name;
    String tel;
    boolean working;

    void print() {
      System.out.println("A.print():");
      //      System.out.printf("  => super.name(%s)\n", super.name);
      //      System.out.printf("  => super.tel(%s)\n", super.tel);
      //      System.out.printf("  => super.working(%s)\n", super.working);
    }
  }

  static class A4 extends A {
    // 필드 오버라이딩
    // - 필드 오버라이딩은 메서드와 달리 변수의 타입이 달라도 된다.
    //
    String working;

    // this.필드명
    // - 현재 클래스에서 해당 필드를 찾는다. 없으면 상위 클래스로 따라 올라가면서 찾는다.
    // super.필드명
    // - 상위 클래스에서부터 해당 필드를 찾는다. 없으면 계속 상위 클래스로 따라 올라간다.
    void print2() {
      System.out.println("A4.print():");
      System.out.printf("  => super.name(%s)\n", super.name);
      System.out.printf("  => super.tel(%s)\n", super.tel);
      System.out.printf("  => super.working(%s)\n", super.working);
    }
  }


  public static void main(String[] args) {
    A4 obj = new A4();
    obj.name = "홍길동"; // A의 name
    obj.tel = "1111-1111"; // A의 tel
    //    obj.working = true; // A4의 working : obj의 클래스에서 먼저 필드를 찾기 때문이다.
    obj.working = "취업";

    obj.print2(); // A4의 print() 호출 : A4에서 상속 받은 print()를 재정의 했기 때문이다.

  }
}
