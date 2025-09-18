// 스태틱 초기화 블록(static initializer) - 중복 로딩 불가 확인
package com.eomcs.oop.ex03;

public class Exam0660 {

  public static class A {
    static {
      int x = 200;
      System.out.println(x);
      System.out.println("Static{} 11111");
    }
    static int a;


    // 클래스가 로딩될 때 스태틱 초기화 블록은 실행된다.
    // 여러 개의 스태틱 블록이 있을 때, 컴파일러는 한 개의 블록으로 합친다.
    // - 바이트코드(Exam0660$A.class)를 확인해 보라.
    //

    static {
      String x = "Hello";
      System.out.println(x);
      System.out.println("Static{} 22222");
    }

    static void m() {}

    static {
      int x = 300;
      System.out.println(x);
      System.out.println("Static{} 33333");
    }
  }

  public static void main(String[] args) throws Exception {

    // 클래스가 로딩되는 경우
    // 1) 클래스 멤버(변수와 메서드)를 사용할 때 클래스가 로딩되어 있지 않다면,
    //    A.a = 100;

    //    A.m();

    // 2) 해당 클래스의 인스턴스를 생성할 때 클래스가 로딩되어 있지 않다면,
    //    new A();

    // 3) 자바에서 제공하는 도구를 사용하여 클래스를 로딩할 때 클래스가 로딩되어 있지 않다면,
    //    Class.forName("com.eomcs.oop.ex03.Exam0660$A");
    //
    // 클래스가 로딩된 후
    // 1) 클래스 필드가 생성된다.
    // 2) 클래스 블록(스태틱 블록)이 실행된다.
    //

    // 레퍼런스를 선언할 때는 클래스가 로딩되지 않는다.
    //    A obj;
    System.out.println("------------------------------");

    // 클래스는 중복 로딩되지 않는다.
    //    A.a = 100;
    //    A.m();
    //    new A();
    //    Class.forName("com.eomcs.oop.ex03.Exam0660$A");
    System.out.println("------");

    // 만약 이전에 클래스가 로딩되었다면, 다시 로딩하지 않는다.
    // - 즉 static 블록을 다시 실행하지 않는다!
    //    new A();

  }
}





