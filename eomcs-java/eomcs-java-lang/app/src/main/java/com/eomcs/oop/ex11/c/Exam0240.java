// inner class : 바깥 클래스의 인스턴스 멤버 접근하기 III
package com.eomcs.oop.ex11.c;

class B4 {

  // 인스턴스 멤버
  int v1 = 10;

  class X {
    int v2 = 100;

    void test() {
      int v3 = 1000;

      // 컴파일러가 변수를 찾을 때 순서: 
      // => 로컬 변수 ---> 인스턴스 변수 ---> 바깥 객체의 인스턴스 변수
      // => 컴파일러는 변수를 찾은 후에 해당 변수의 문법에 맞게 코드를 변경한다.
      //
      System.out.printf("v3 = %d\n", v3); // 로컬 변수: v3 
      System.out.printf("this.v2 = %d\n", v2); // 인스턴스 변수: this.v2 로 변경 
      System.out.printf("B4.this.v1 = %d\n", v1); // 바깥 객체의 인스턴스 변수: B4.this.v1 으로 변경 
    }
  }
}

public class Exam0240 {

  public static void main(String[] args) {
    B4 outer = new B4();
    outer.v1 = 11;

    B4.X x1 = outer.new X();
    x1.test();
    System.out.println("--------------------");

    B4.X x2 = outer.new X();
    x2.test();
    System.out.println("--------------------");

    B4 outer2 = new B4();
    outer2.v1 = 22;

    B4.X x3 = outer2.new X();
    x3.test();
    System.out.println("--------------------");
  }

}
