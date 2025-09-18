// inner class : inner 클래스에서 변수를 찾는 순서 II
package com.eomcs.oop.ex11.c;

class H {
  int v1 = 1;
  int v2 = 2;
  int v3 = 3; 

  class X {
    int v1 = 10;
    int v2 = 20;

    void m1(int v1) {

      // this 를 명시하지 않았을 때 변수를 찾는 순서
      // 1) 로컬 변수를 찾는다.
      // 2) 메서드가 소속된 클래스의 인스턴스 필드를 찾는다.
      // 3) 바깥 클래스의 인스턴스나 스태틱 필드를 찾는다.

      System.out.printf("v1 = %d\n", v1);
      System.out.printf("this.v1 = %d\n", this.v1);
      System.out.printf("H.this.v1 = %d\n", H.this.v1);

      System.out.printf("v2 = %d\n", v2); // ==> 컴파일러는 this.v2 로 변경한다.
      System.out.printf("v3 = %d\n", v3); // ==> 컴파일러는 H.this.v3 로 변경한다.
    }
  }
}

public class Exam0620 {

  public static void main(String[] args) {
    H outer = new H();

    H.X obj = outer.new X();
    obj.m1(100);

  }

}
