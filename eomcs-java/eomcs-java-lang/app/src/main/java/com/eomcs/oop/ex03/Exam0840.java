// 변수 초기화 문장(variable initializer) - 변수 초기화 문장 순서
package com.eomcs.oop.ex03;

public class Exam0840 {

  static class A {

    int b = 200;
    int c;

    // 인스턴스 필드 초기화 문장은
    // 선언된 순서대로 모든 생성자의 앞 부분에 삽입된다.
    // - 바이트코드(Exam0840$A.class)를 확인해 보라!

    public A() {
      // 즉 인스턴스 필드를 초기화시키는 문장이 다음과 같이 삽입된다.
      //
      // b = 200;
      // a = 100;

      c = 300;
    }

    int a = 100;
  }

  public static void main(String[] args) {
    A obj1 = new A();
    System.out.printf("a=%d, b=%d, c=%d\n", obj1.a, obj1.b, obj1.c);
  }
}


