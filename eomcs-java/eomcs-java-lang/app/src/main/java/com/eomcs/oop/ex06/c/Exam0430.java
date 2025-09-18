// 오버라이딩(overriding) - 준비
package com.eomcs.oop.ex06.c;



public class Exam0430 {

  static class A {
    String name = "A";
    boolean working = true;

    void print() {
      System.out.println("A.print():");
      System.out.printf("  => this.name(%s)\n", this.name);
      System.out.printf("  => this.working(%s)\n", this.working);
    }

    void m1() {
      System.out.println("A.m1()");   
    }
  }


  static class A2 extends A {
    String name = "A2";
    int age = 20;

    @Override
    void print() {
      System.out.println("A2.print():");
      System.out.printf("  => this.name(%s), super.name(%s)\n",
          this.name, super.name);
      System.out.printf("  => this.working(%s), super.working(%s)\n",
          this.working, super.working);
      System.out.printf("  => this.age(%s), super.age(컴파일 오류!) \n",
          this.age /*, super.age*/);

      this.m1();
      super.m1();
    }

    @Override
    void m1() {
      System.out.println("A2.m1()");   
    }
  }


  static class A3 extends A2 {
    String name = "A3";
    String gender = "남";

    @Override
    void m1() {
      System.out.println("A3.m1()");   
    }

  }

  public static void main(String[] args) {
    A2 obj = new A3();
    obj.print();
  }
}
