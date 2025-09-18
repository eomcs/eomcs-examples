// 메서드 정보 추출 - 스태틱 메서드 호출
package com.eomcs.reflect.ex03;

import java.lang.reflect.Method;

public class Exam0310 {

  // 스태틱 메서드
  public static void plus(int a, int b) {
    System.out.printf("합계: %d\n", a + b);
  }

  public static void main(String[] args) throws Exception {
    Class<?> clazz = Exam0310.class;

    // 스태틱 메서드 호출 방법
    // Method.invoke(인스턴스, 아규먼트, ...);
    //

    // 1) 스태틱 메서드를 찾아 호출하기
    Method m = clazz.getMethod("plus", int.class, int.class);

    // => 스태틱 메서드이기 때문에 인스턴스는 지정할 필요가 없다.
    m.invoke(null, 10, 20);

    // => 클래스 메서드를 호출하는 일반적인 방식
    Exam0310.plus(10, 20);
  }

}


