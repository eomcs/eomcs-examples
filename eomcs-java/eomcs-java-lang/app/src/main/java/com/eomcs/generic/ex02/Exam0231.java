// 제네릭(Generic) 문법 정리 - 제네릭 객체의 메서드 사용 
package com.eomcs.generic.ex02;

import java.util.ArrayList;

public class Exam0231 {

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

    ArrayList<B1> list1 = new ArrayList<>();

    // ArrayList의 항목 타입을 B1으로 지정했기 때문에  
    // ArrayList의 메서드 파라미터나 리턴 타입은 B1으로 설정된다.

    // => add(B1)
    //    list1.add(new Object()); // 컴파일 오류!
    //    list1.add(new String()); // 컴파일 오류!
    //    list1.add(new Integer(100)); // 컴파일 오류!
    //    list1.add(new Member("홍길동", 20)); // 컴파일 오류!
    //    list1.add(new A()); // 컴파일 오류!
    list1.add(new B1());
    //    list1.add(new B2()); // 컴파일 오류!
    list1.add(new C());

    System.out.println("종료!");
  }
}








