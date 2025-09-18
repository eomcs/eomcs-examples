// 메서드 정보 추출 - 배열 파라미터를 가지는 메서드 호출
package com.eomcs.reflect.ex03;

import java.lang.reflect.Method;

public class Exam0420 {

  // 배열 파라미터
  public static void print(String[] names) {
    System.out.print("==> ");
    for (String name : names) {
      System.out.print(name + ",");
    }
    System.out.println();
  }

  public static void main(String[] args) throws Exception {

    Method m = Exam0420.class.getMethod("print", String[].class);

    System.out.println(m);

    // 배열 파라미터인 경우 배열에 담아서 전달
    m.invoke(null, (Object) new String[] {"홍길동", "임꺽정", "유관순"});

    // 다음과 같이 낱개로 전달할 수 없다.
    //    m.invoke(null, "홍길동", "임꺽정", "유관순"); // 예외 발생!
  }

}


