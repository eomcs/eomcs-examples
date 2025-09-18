// 스태틱 초기화 블록(static initializer) - 변수 초기화 문장(variable initializer)
package com.eomcs.oop.ex03;

public class Exam0680 {

  public static class A {
    static {
      b = 400;
    }

    static int a = 100; 

    static {
      a = 200;
      System.out.println("static {} 실행");
    }

    static int b = 300;

    // 변수 초기화 문장(variable initializer)을 컴파일 할 때,
    // 1) 스태틱 변수 선언에서 변수 초기화 문장을 별도의 스태틱 블록으로 분리한다.
    //
    //     static {
    //       b = 400;
    //     }
    //     static int a;
    //     static {
    //       a = 100;
    //     }
    //     static {
    //       a = 200;
    //       System.out.println("static {} 실행");
    //     }
    //     static int b;
    //     static {
    //       b = 300;
    //     }
    // 2) 스태틱 블록을 한 개의 블록으로 합친다.
    //     static int a;
    //     static int b;
    //
    //     static {
    //       b = 400;
    //       a = 100;
    //       a = 200;
    //       System.out.println("static {} 실행");
    //       b = 300;
    //     }
    // - 바이트 코드(Exam0680$A.class)를 확인해 보라!
  }

  public static void main(String[] args) throws Exception {

    System.out.println(A.a);
    System.out.println(A.b);
  }
}


