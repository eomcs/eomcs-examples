// 오버라이딩(overriding) - 레퍼런스가 가리키는 필드
package com.eomcs.oop.ex06.c;

public class Exam0510 {

  static class A {
    String name = "A";
    String tel = "A: 010-1111-1111";
    boolean working = true;
  }

  static class A2 extends A {
    int age = 20;
  }

  static class A3 extends A {
    int age = 30;
    String tel = "A3: 010-1111-2222";
  }

  static class A4 extends A3 {
    String age = "40";
    boolean working = false;
  }


  public static void main(String[] args) {
    A4 obj1 = new A4();

    System.out.println(obj1.name); // "A"
    System.out.println(obj1.age); // "40"
    System.out.println(obj1.working); // false
    System.out.println("-----------------------------");

    // 레퍼런스를 형변환 하면 오버라이딩 하기 전,
    // 해당 클래스의 인스턴스 변수를 가리킬 수 있다.
    System.out.println(((A3)obj1).age);  // 30
    System.out.println(((A3)obj1).working); // true -> 없으면 수퍼 클래스의 필드를 가리킨다.
    System.out.println("-----------------------------");

    // 형변환 클래스에 존재하지 않는 필드는 가리킬 수 없다.
    //    System.out.println(((A)obj1).age); // 컴파일 오류!
    System.out.println(((A)obj1).working); // true
  }
}








