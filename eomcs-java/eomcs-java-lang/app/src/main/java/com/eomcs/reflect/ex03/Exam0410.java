// 메서드 정보 추출 - 여러 개의 파라미터를 가지는 메서드 호출
package com.eomcs.reflect.ex03;

import java.lang.reflect.Method;

public class Exam0410 {

  public static void print(String name, int a, int b, int c) {
    System.out.printf("==> %s: %d\n", name, (a + b + c));
  }

  public static void main(String[] args) throws Exception {
    Method m = Exam0410.class.getMethod("print", String.class, int.class, int.class, int.class);

    System.out.println(m);

    // 파라미터 값을 낱개로 전달하기
    m.invoke(null, "홍길동", 100, 90, 80);

    // 파라미터 값을 배열에 담아서 전달할 수 있다.
    m.invoke(null, new Object[] {"홍길동", 100, 100, 100});
  }
}


