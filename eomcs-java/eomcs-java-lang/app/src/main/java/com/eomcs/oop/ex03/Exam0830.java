// 변수 초기화 문장(variable initializer) - 여러 개의 생성자
package com.eomcs.oop.ex03;

public class Exam0830 {

  static class A {
    int a = 100;
    int b = 200;
    int c;

    // 인스턴스 필드 초기화 문장은
    // 생성자의 앞 부분에 삽입된다.
    // 여러 개의 생성자가 있으면 모든 생성자에 삽입된다.
    // - 바이트코드(Exam0830$A.class)를 확인해 보라!

    public A() {
      // 즉 인스턴스 필드를 초기화시키는 문장이 다음과 같이 삽입된다.
      //
      // a = 100;
      // b = 200;

      c = 300;
    }

    public A(int a) {
      // 즉 인스턴스 필드를 초기화시키는 문장이 다음과 같이 삽입된다.
      //
      // a = 100;
      // b = 200;

      this.a = a; //111
      c = 300;
    }

    public A(int a, int b) {
      // 즉 인스턴스 필드를 초기화시키는 문장이 다음과 같이 삽입된다.
      //
      // a = 100;
      // b = 200;

      this.a = a; // 111
      this.b = b; // 222
      c = 300;
    }
  }

  public static void main(String[] args) {
    A obj1 = new A();
    System.out.printf("a=%d, b=%d, c=%d\n", obj1.a, obj1.b, obj1.c);
    System.out.println("--------------------------");

    A obj2 = new A(111);
    System.out.printf("a=%d, b=%d, c=%d\n", obj2.a, obj2.b, obj2.c);
    System.out.println("--------------------------");

    A obj3 = new A(111, 222);
    System.out.printf("a=%d, b=%d, c=%d\n", obj3.a, obj3.b, obj3.c);
    System.out.println("--------------------------");
  }
}


