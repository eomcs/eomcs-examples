// 클래스 정보 추출 - 클래스의 수퍼 클래스 정보 알아내기
package com.eomcs.reflect.ex02;

public class Exam0210 {
  static class A {
  }
  static class B extends A {
  }
  static class C extends B {
  }

  public static void main(String[] args) throws Exception {
    Class<?> clazz = Class.forName("com.eomcs.reflect.ex02.Exam0210$C");

    // 수퍼 클래스의 타입을 알아내기
    Class<?> superClazz = clazz.getSuperclass();
    System.out.println(superClazz.getName());
    System.out.println(superClazz.getSuperclass().getName());


  }

}
