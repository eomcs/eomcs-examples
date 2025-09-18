// 변수 초기화 문장(variable initializer) - 인스턴스 초기화 블록과 필드 초기화, 생성자의 실행 순서
package com.eomcs.oop.ex03;

public class Exam0860 {

  static class A {
    // 문법을 헷갈리지 않도록 하기 위해서 가능한 다음 순서로 코드를 작성하라.
    // 1) 필드 선언 및 초기화 문장
    // 2) 스태틱 블록 (가능한 한 개의 블록으로 묶어라.)
    // 3) 인스턴스 블록 (가능한 한 개의 블록으로 묶어라.)
    // 4) 생성자 (기본 생성자를 먼저 두고 파라미터 개수에 따라 나열하라.)
    //

    // 필드 선언 및 초기화 문장(variable initializer)
    int a = 100;

    // 인스턴스 초기화 블록(initializer block)
    {
      this.a = 200;
      System.out.println("초기화 블록");
    }

    A() {
      // a = 100;
      // a = 200;
      // System.out.println("초기화 블록");

      System.out.println("A()");
    }

    A(int a) {
      // a = 100;
      // a = 200;
      // System.out.println("초기화 블록");

      System.out.println("A(int)");
      this.a = a;
    }
  }

  public static void main(String[] args) {
    A obj1 = new A();
    System.out.println(obj1.a);

    System.out.println("-------------------");

    A obj2 = new A(1111);
    System.out.println(obj2.a);
  }
}


