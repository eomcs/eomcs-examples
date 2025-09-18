// Reflection API : 클래스 정보를 얻는 방식 - forName()과 "class"라는 스태틱 변수
package com.eomcs.reflect.ex01;

import java.io.File;

public class Exam0210 {

  static class A {
    static int s_var = 100;
    int i_var = 200;

    static void s_m() {}

    void i_m() {}

    static {
      System.out.println("A 클래스 로딩!");
    }
  }

  public static void main(String[] args) throws Exception {
    // 다음은 클래스를 로딩한 후 스태틱 필드를 생성하고 스태틱 블록을 실행한다.

    //    Class clazz = Class.forName("com.eomcs.reflect.ex01.Exam0210$A");
    System.out.println("-----------------------------");

    //    A.s_var = 200;

    // 자바의 모든 클래스는 "class"라는 특별한 스태틱 변수를 갖고 있다.
    // "class" 변수에는 해당 클래스의 정보를 담은 Class 객체의 주소가 저장되어 있다.
    Class clazz1 = A.class;
    // 하지만 이 방식은 static {} 블록을 실행하지 않는다.
    // 물론 스태틱 멤버를 사용하는 최초의 순간에는 static 블록이 실행될 것이기 때문에
    // "클래스 로딩 후 스태틱 블록 실행" 명제는 지켜진다.

    //    A.s_var = 300;

    // 클래스 정보를 얻기 위해 A.class 처럼 class 라는 스태틱 변수를 사용하는 것이
    // forName()을 호출하는 것 보다 안 좋은 이유는?
    // - forName()의 파라미터는 문자열이다.
    // - 즉 외부에서 문자열을 입력 받아 해당 클래스를 임의로 로딩할 수 있지만,
    //   "class"라는 스태틱 변수를 사용하는 것은
    //   자바 소스 안에 명확히 해당 클래스를 지정(하드코딩)해야 한다.
    // - 이런 방식은 다른 클래스를 로딩하려면 다시 코드를 변경해야 하는 문제가 있다.

    Class clazz2 = String.class;
    Class clazz3 = System.class;
    Class clazz4 = File.class;
    Class clazz5 = int.class;

    System.out.println("실행 완료!");
  }

}


