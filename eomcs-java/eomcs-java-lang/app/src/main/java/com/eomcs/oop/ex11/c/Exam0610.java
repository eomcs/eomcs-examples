// inner class : inner 클래스에서 변수를 찾는 순서
package com.eomcs.oop.ex11.c;

class G {
  int v1 = 1;
  int v2 = 2;
  int v3 = 3; 

  class X {
    int v1 = 10;
    int v2 = 20;

    void m1(int v1) {

      // 중첩 클래스의 메서드에서 필드를 사용하기 
      System.out.println("G 객체:");
      System.out.printf("G.this.v1 = %d\n", G.this.v1);
      System.out.printf("G.this.v2 = %d\n", G.this.v2);
      System.out.printf("G.this.v3 = %d\n", G.this.v3);

      System.out.println("-------------------------");

      // inner 클래스의 인스턴스 필드 접근
      System.out.println("G.X 객체:");
      System.out.printf("this.v1 = %d\n", this.v1);
      System.out.printf("this.v2 = %d\n", this.v2);

      System.out.println("-------------------------");

      // 로컬 변수 접근
      System.out.println("로컬:");
      System.out.printf("v1 = %d\n", v1);

      System.out.println("-------------------------");
    }
  }
}

public class Exam0610 {

  public static void main(String[] args) {
    G outer = new G();

    G.X obj = outer.new X();
    obj.m1(100);

  }

}
