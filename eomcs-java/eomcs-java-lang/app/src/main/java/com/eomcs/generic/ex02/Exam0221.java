// 제네릭(Generic) 문법 정리 - 제네릭 파라미터 
package com.eomcs.generic.ex02;

import java.util.ArrayList;

public class Exam0221 {

  static class A {}
  static class B1 extends A {}
  static class B2 extends A {}
  static class C extends B1 {}
  /*
   *   Object
   *     |
   *     A
   *    / \
   *   B1 B2
   *   |
   *   C
   */

  public static void main(String[] args) {
    // m1(ArrayList<Object>)
    // => Object 로 지정된 ArrayList 만 파라미터로 넘길 수 있다.
    // => Object의 하위 클래스는 안된다.

    //    m1(new ArrayList()); // 하지마! 
    m1(new ArrayList<Object>());
    m1(new ArrayList<>()); // 생략하면 파라미터의 제네릭 타입과 같다.

    //    m1(new ArrayList<A>());  // 컴파일 오류!
    //    m1(new ArrayList<B1>()); // 컴파일 오류!
    //    m1(new ArrayList<B2>()); // 컴파일 오류!
    //    m1(new ArrayList<C>());  // 컴파일 오류!
  }

  static void m1(ArrayList<Object> list) {
    list.add(new Object());
    list.add(new A());
    list.add(new B1());
    list.add(new B2());
    list.add(new C());
  }
}








