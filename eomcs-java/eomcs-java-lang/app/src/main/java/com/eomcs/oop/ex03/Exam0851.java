// 변수 초기화 문장(variable initializer) - 인스턴스 초기화 블록과 필드 초기화, 생성자의 실행 순서
package com.eomcs.oop.ex03;

public class Exam0851 {

  static class A {

    int b = 200;
    int c;

    A() {
      // 자바 컴파일러는
      // 인스턴스 초기화 블록이나 필드 초기화 문장이 있다면,
      // 종류에 구분없이 선언된 순서 그대로 모든 생성자의 처음 부분에 복사한다.
      // 즉 다음과 같다.
      //
      // b = 200;
      // a = 100;
      // a = 111;
      // System.out.println("초기화 블록 실행");

      System.out.println("A()");
    }

    // 필드 초기화 문장(variable initializer)
    int a = 100;

    // 인스턴스 초기화 블록(initializer block)
    {
      a = 111;
      System.out.println("초기화 블록 실행");
    }
  }

  public static void main(String[] args) {
    A obj1 = new A();
    System.out.printf("a = %d, b = %d, c = %d\n", obj1.a, obj1.b, obj1.c);
  }
}


