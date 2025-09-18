// 제네릭(Generic) 문법 정리 - 레퍼런스와 인스턴스 생성
package com.eomcs.generic.ex02;

import java.util.ArrayList;

public class Exam0213 {

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

    ArrayList<A> list1;

    //    list1 = new ArrayList(); // 이렇게 사용하지 말고, 명확히 제네릭의 타입을 지정하라.
    //    list1 = new ArrayList<Object>(); // 컴파일 오류!
    list1 = new ArrayList<A>();
    list1 = new ArrayList<>(); // 생략하면 레퍼런스와 같은 제네릭 타입이 된다.
    //    list1 = new ArrayList<B1>(); // 컴파일 오류!
    //    list1 = new ArrayList<B2>(); // 컴파일 오류!
    //    list1 = new ArrayList<C>(); // 컴파일 오류!

    // ArrayList의 타입 파라미터가 A로 결정되었기 때문에
    // add() 메서드의 파라미터 타입은 A가 된다.
    // 따라서 A 타입 파라미터에 전달 할 수 있는 값을 모두 가능!
    list1.add(new A());
    list1.add(new B1());
    list1.add(new B2());
    list1.add(new C());

    ArrayList<B2> list2;

    //    list2 = new ArrayList(); // 이렇게 사용하지 말고, 명확히 제네릭의 타입을 지정하라.
    //    list2 = new ArrayList<Object>(); // 컴파일 오류!
    list2 = new ArrayList<B2>();
    list2 = new ArrayList<>(); // B2를 생략한 문법이다.
    //    list2 = new ArrayList<A>(); // 컴파일 오류!
    //    list2 = new ArrayList<B1>(); // 컴파일 오류!
    //    list2 = new ArrayList<C>(); // 컴파일 오류!

  }
}








