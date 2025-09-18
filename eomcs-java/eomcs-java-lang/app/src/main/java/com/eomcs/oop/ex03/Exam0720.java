// 인스턴스 초기화 블록(instance initializer) - 인스턴스 초기화 블록과 생성자
package com.eomcs.oop.ex03;

public class Exam0720 {

  static class A {
    int a;
    int b;
    int c;

    // 인스턴스 초기화 블록 사용 후
    // - 여러 생성자에 공통으로 존재하는 코드가 있다면
    //   별도의 블록으로 뽑아내는 것이 소스 코드 관리에 좋다.
    // - 이럴 때 사용하라고 만든 문법이 인스턴스 초기화 블록이다.
    // - 다음과 같이 인스턴스 초기화 블록을 사용하여 생성자에 공통으로 들어갈
    //   코드를 작성하면 된다.
    //
    {
      a = 100;
      System.out.println("인스턴스 초기화 블록 실행!");
    }

    // 생성자가 있으면,
    // - 존재하는 생성자의 앞 부분에 삽입된다.
    // - 바이트코드(Exam0720$A.class)를 확인해 보라!
    public A() {
      a = 200;
      System.out.println("A() 생성자 호출");
    }
  }

  public static void main(String[] args) {
    A obj1 = new A();
    System.out.printf("a=%d, b=%d, c=%d\n", obj1.a, obj1.b, obj1.c);
  }
}


