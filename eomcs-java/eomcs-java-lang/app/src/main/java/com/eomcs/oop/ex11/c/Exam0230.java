// inner class : 바깥 클래스의 인스턴스 멤버 접근하기 II
package com.eomcs.oop.ex11.c;

class B3 {

  // 인스턴스 멤버
  int v1 = 10;

  class X {
    // non-static nested class 는 바깥 클래스의 인스턴스 주소를 저장할 변수를 갖고 있다.
    // 예) 
    //    B3 this$0;

    int v1 = 100;

    // non-static nested class 는 바깥 클래스의 인스턴스 주소를 받는 생성자를 갖고 있다.
    // 예)
    //    X(B3 obj) {
    //      this$0 = obj;
    //    }


    void test() {
      int v1 = 1000;

      System.out.printf("v1 = %d\n", v1); // 로컬 변수 
      System.out.printf("this.v1 = %d\n", this.v1); // 인스턴스 변수 
      System.out.printf("B3.this.v1 = %d\n", B3.this.v1); // 바깥 객체의 인스턴스 변수 
    }
  }
}

public class Exam0230 {

  public static void main(String[] args) {
    B3 outer = new B3();
    outer.v1 = 11;

    B3.X x1 = outer.new X();
    x1.test();
    System.out.println("--------------------");

    B3.X x2 = outer.new X();
    x2.test();
    System.out.println("--------------------");

    B3 outer2 = new B3();
    outer2.v1 = 22;

    B3.X x3 = outer2.new X();
    x3.test();
    System.out.println("--------------------");
  }

}
