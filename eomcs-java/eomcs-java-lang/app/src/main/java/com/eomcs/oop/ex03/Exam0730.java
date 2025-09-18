// 인스턴스 초기화 블록(instance initializer) - 인스턴스 초기화 블록과 여러 개의 생성자
package com.eomcs.oop.ex03;

public class Exam0730 {

  static class A {
    int a;
    int b;
    int c;

    A() {
      // 자바 컴파일러는 필드 초기화(variable initializer)나 
      // 인스턴스 초기화 블록(initializer block)이 있으면 
      // 선언된 순서대로 모든 생성자의 첫 부분에 복사한다.
      // 즉 선언된 초기화 블록에 안에 있는 코드가 그대로 다음과 같이 
      // 복사된다.
      // Java Tutorial 문서: Learning the Java Language > Classes and Objects
      // 'The Java compiler copies initializer blocks 
      //  into every constructor. 
      //  Therefore, this approach can be used 
      //  to share a block of code 
      //  between multiple constructors.'
      // 
      // a = 100;
      // System.out.println("Hello!");

      System.out.println("A()");
      b = 200;
      c = 300;
    }

    A(int b) {
      // 자바 컴파일러는 이 생성자에도 인스턴스 블록의 코드를 그대로 복사한다.
      // a = 100;
      // System.out.println("Hello!");

      System.out.println("A(int)");
      this.b = b;
      this.c = 300;
    }

    A(int b, int c) {
      // 자바 컴파일러는 이 생성자에도 인스턴스 블록의 코드를 그대로 복사한다.
      // a = 100;
      // System.out.println("Hello!");

      System.out.println("A(int, int)");
      this.b = b;
      this.c = c;
    }

    { // 인스턴스 초기화 블록 (initializer block)
      a = 100;
      System.out.println("Hello!");
    }
    // 여러 개의 생성자가 있으면,
    // - 인스턴스 초기화 블록 코드는 존재하는 모든 생성자의 앞 부분에 삽입된다.
    // - 바이트코드(Exam0730$A.class)를 확인해 보라!
  }
  public static void main(String[] args) {
    A obj1 = new A();
    System.out.printf("a=%d, b=%d, c=%d\n", 
        obj1.a, obj1.b, obj1.c);
    System.out.println("--------------------------");

    A obj2 = new A(222);
    System.out.printf("a=%d, b=%d, c=%d\n", 
        obj2.a, obj2.b, obj2.c);
    System.out.println("--------------------------");

    A obj3 = new A(222, 333);
    System.out.printf("a=%d, b=%d, c=%d\n", 
        obj3.a, obj3.b, obj3.c);
    System.out.println("--------------------------");
  }
}





