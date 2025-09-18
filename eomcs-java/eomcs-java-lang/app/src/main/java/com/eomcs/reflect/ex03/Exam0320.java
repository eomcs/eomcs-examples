// 메서드 정보 추출 - 인스턴스 메서드 호출
package com.eomcs.reflect.ex03;

import java.lang.reflect.Method;

public class Exam0320 {

  // 인스턴스 메서드
  public void minus(int a, int b) {
    System.out.printf("빼기: %d\n", a - b);
  }

  public static void main(String[] args) throws Exception {
    Class<?> clazz = Exam0320.class;

    // 메서드 호출 방법
    // Method.invoke(인스턴스, 아규먼트, ...);
    //

    // 1) 인스턴스 메서드를 찾아 호출하기
    Method m = clazz.getMethod("minus", int.class, int.class);

    // => 인스턴스 메서드를 호출할 때는 반드시 인스턴스 주소를 넘겨야 한다.
    Exam0320 obj = new Exam0320();
    m.invoke(obj, 10, 20); // 리플렉션 API를 사용하여 인스턴스 메서드 호출

    // => 인스턴스 메서드를 호출하는 일반적인 방식
    obj.minus(10, 20);

    // => 인스턴스 주소를 넘겨주지 않으면 NullPointerException 발생!
    //    m.invoke(null, 10, 20);

  }

}


