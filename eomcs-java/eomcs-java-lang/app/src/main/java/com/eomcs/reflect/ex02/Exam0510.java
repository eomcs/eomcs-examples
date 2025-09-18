// 클래스 정보 추출 - 패키지 정보 알아내기
package com.eomcs.reflect.ex02;

public class Exam0510 {

  static interface A {
  }

  static interface B {
  }

  static interface C {
  }

  static class D implements A, B, C {
  }

  public static void main(String[] args) throws Exception {
    Class<?> clazz = Class.forName("com.eomcs.reflect.ex02.Exam0510$D");

    // 해당 클래스의 패키지 정보를 가져온다.
    Package p = clazz.getPackage();
    System.out.println(p.getName());
  }

}
