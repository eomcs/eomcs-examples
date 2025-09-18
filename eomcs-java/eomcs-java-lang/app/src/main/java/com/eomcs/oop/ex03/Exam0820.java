// 변수 초기화 문장(variable initializer) - 변수 초기화 문장과 생성자
package com.eomcs.oop.ex03;

public class Exam0820 {

  static class A {
    int a = 100;
    int b = 200;
    int c;

    // 인스턴스 필드 초기화 문장은
    // 생성자의 앞 부분에 삽입된다.
    //
    public A() {
      // 생성자가 있으면,
      // - 존재하는 생성자의 앞 부분에 삽입된다.
      // - 바이트코드(Exam0820$A.class)를 확인해 보라!
      // - 즉 인스턴스 필드를 초기화시키는 문장이 다음과 같이 삽입된다.
      //
      // a = 100;
      // b = 200;

      a = 111;
      c = 333;
    }
  }

  public static void main(String[] args) {
    A obj1 = new A();
    System.out.printf("a=%d, b=%d, c=%d\n", obj1.a, obj1.b, obj1.c);
  }
}


