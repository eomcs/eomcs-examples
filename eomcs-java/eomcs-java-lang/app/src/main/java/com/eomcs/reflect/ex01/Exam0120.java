// Reflection API : 중첩 클래스 로딩
package com.eomcs.reflect.ex01;

class B {
  static {
    System.out.println("B 클래스 로딩됨!");
  }

  static class C {
    static int s_var = 100;
    int i_var = 200;

    static void s_m() {}

    void i_m() {}

    static {
      System.out.println("B의 중첩클래스 C 로딩됨!");
    }
  }
}


public class Exam0120 {

  public static void main(String[] args) throws Exception {
    //    Class.forName("com.eomcs.reflect.ex01.B");
    // => 바깥 클래스가 로딩되었다고 중첩 클래스가 자동으로 로딩되는 것은 아니다.

    Class.forName("com.eomcs.reflect.ex01.B$C");
    // => 중첩 클래스는 "바깥클래스명$중첩클래스명" 형식의 이름을 갖는다.
    // => 스태틱 중첩 클래스를 로딩할 때는 바깥 클래스를 로딩하지는 않는다.
    //    스태틱 중첩 클래스 입장에서는 바깥 클래스가 패키지의 역할을 할 뿐이다.
    // => 바깥 클래스 파일이 존재하지 않더라도 실행 오류가 발생하지 않는다.
    //
    // 주의!
    // => 자바 코드로 중첩 클래스를 표현할 때는
    //    클래스 이름에 $ 를 붙이지 않고 . 을 붙인다.
    //    com.eomcs.reflect.ex01.B.C obj;
    //    obj = new com.eomcs.reflect.ex01.B.C();

    System.out.println("실행 완료!");
  }

}


