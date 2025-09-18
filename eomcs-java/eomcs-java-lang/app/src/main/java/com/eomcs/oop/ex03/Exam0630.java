// 스태틱 초기화 블록(static initializer) - 클래스 메서드 사용
package com.eomcs.oop.ex03;

public class Exam0630 {

  public static class A {
    static int a;

    static void m() {
      System.out.println("A.m()");
    }

    // 클래스가 로딩될 때 스태틱 초기화 블록은 실행된다.
    // 여러 개의 스태틱 블록이 있을 때, 컴파일러는 한 개의 블록으로 합친다.
    // - 바이트코드(Exam0630$A.class)를 확인해 보라.
    //
    static {
      System.out.println("Static{} 11111");
    }

    static {
      System.out.println("Static{} 22222");
    }
  }

  public static void main(String[] args) throws Exception {
    System.out.println("main() 호출됨!");

    // 클래스가 로딩되는 경우,
    // 2) 클래스 멤버(필드와 메서드)를 사용할 때,
    //    클래스가 로딩된 상태가 아니라면 클래스를 로딩한다.
    // 
    A.m();
    System.out.println("종료!");
  }
}


