// 클래스 정보 추출 - 구현 인터페이스 정보 알아내기 II
package com.eomcs.reflect.ex02;

public class Exam0420 {

  static interface X {
  }

  static interface A {
  }

  static interface B extends X {
  }

  static interface C {
  }

  static class D implements A, B, C {
  }

  public static void main(String[] args) throws Exception {
    Class<?> clazz = Class.forName("com.eomcs.reflect.ex02.Exam0410$D");

    // 해당 클래스가 구현한 인터페이스 정보를 가져온다.
    // => 인터페이스의 수퍼 인터페이스는 가져오지 않는다.
    Class<?>[] list = clazz.getInterfaces();
    for (Class<?> c : list) {
      System.out.println(c.getName());
    }

  }

}
