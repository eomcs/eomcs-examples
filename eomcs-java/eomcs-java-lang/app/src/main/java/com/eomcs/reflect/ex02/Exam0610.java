// 클래스 정보 추출 - 클래스의 상속 관계 판단하기
package com.eomcs.reflect.ex02;

public class Exam0610 {

  static class A {}

  static class B extends A {}

  static class C extends B {}


  public static void main(String[] args) throws Exception {
    System.out.println(A.class.isAssignableFrom(A.class));
    System.out.println(A.class.isAssignableFrom(B.class));
    System.out.println(A.class.isAssignableFrom(C.class));
    System.out.println("-----------------------------");

    System.out.println(B.class.isAssignableFrom(A.class));
    System.out.println(B.class.isAssignableFrom(B.class));
    System.out.println(B.class.isAssignableFrom(C.class));
    System.out.println("-----------------------------");

    System.out.println(C.class.isAssignableFrom(A.class));
    System.out.println(C.class.isAssignableFrom(B.class));
    System.out.println(C.class.isAssignableFrom(C.class));
    System.out.println("-----------------------------");
  }

}
