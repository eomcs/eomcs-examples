// nested class : 로컬 클래스의 접근 제어
package com.eomcs.oop.ex11.a;

public class Exam0311 {

  static void m1() {
    int a;
    // 로컬 변수처럼 로컬 클래스에는 접근 제어 modifier 를 붙일 수 없다.
    //    private class A1 {} // 컴파일 오류!
    //    protected class A2 {} // 컴파일 오류!
    //    public class A3 {} // 컴파일 오류!

    class A4 {} // OK!
  }

  void m2() {
    // 로컬 변수처럼 로컬 클래스에는 접근 제어 modifier 를 붙일 수 없다.
    //    private class B1 {} // 컴파일 오류!
    //    protected class B2 {} // 컴파일 오류!
    //    public class B3 {} // 컴파일 오류!

    class B4 {} // OK!
  }
}