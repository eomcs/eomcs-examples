// 메서드 정보 추출 - 특정 메서드만 추출
package com.eomcs.reflect.ex03;

import java.lang.reflect.Method;

public class Exam0210 {
  public static void m1() {}

  public void m2() {}

  protected void m3() {}

  void m4() {}

  private void m5() {}

  public static void main(String[] args) throws Exception {
    Class<?> clazz = Exam0210.class;

    // 해당 클래스에 선언된 메서드와 상속 받은 메서드까지 포함하여
    // 파라미터가 없는 "m3" 이름을 가진 public 메서드 추출
    //    Method m0 = clazz.getMethod("m3"); // public이 아니기 때문에 못 찾는다.

    Method m = clazz.getMethod("m1"); // OK!
    System.out.println(m.getName());

    System.out.println(clazz.getMethod("toString").getName());
    System.out.println("----------------------");

    // => public 이 아닌 메서드를 찾고 싶다면,
    m = clazz.getDeclaredMethod("m3"); // OK
    System.out.println(m.getName());

    // => 단 현재 클래스에 정의된 메서드를 찾는다.
    // => 상속 받은 메서드는 제외한다.
    System.out.println(clazz.getDeclaredMethod("toString")); // 예외 발생!
    // 상속 받은 메서드는 못찾는다.
  }

}


