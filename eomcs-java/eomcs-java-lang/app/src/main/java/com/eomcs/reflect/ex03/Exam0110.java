// 메서드 정보 추출 - getMethod()
package com.eomcs.reflect.ex03;

import java.lang.reflect.Method;

public class Exam0110 {
  public static void m1() {}

  public void m2() {}

  protected void m3() {}

  void m4() {}

  private void m5() {}

  public static void main(String[] args) {
    Class<?> clazz = Exam0110.class;

    // 클래스에서 메서드 정보를 추출하기
    // => 해당 클래스에 선언된 public 메서드 + 상속 받은 public 메서드
    Method[] list = clazz.getMethods();
    for (Method m : list) {
      System.out.println(m.getName());
    }
  }

}


