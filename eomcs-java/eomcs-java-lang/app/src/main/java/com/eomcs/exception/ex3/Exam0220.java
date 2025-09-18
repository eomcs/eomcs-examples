// 예외 던지기 - RuntimeException 예외를 던질 경우
package com.eomcs.exception.ex3;

public class Exam0220 {

  // RuntimeException 클래스는 Exception의 서브클래스이다.
  static void m() throws RuntimeException {
    throw new RuntimeException(); // OK!
  }

  // Exception의 서브 클래스임에도 불구하고
  // RuntimeException 객체를 던질 경우,
  // 메서드 선언부에 예외를 던진다고 표시하지 않아도 된다.
  // => "Unchecked Exception"이라 부른다.
  //    즉, 해당 메서드가 예외를 던지는지 검사하지 않는다는 뜻이다.
  // => 보통 스텔스 모드(비유!)로 예외를 전달할 때 사용한다.
  //
  static void m2() {
    throw new RuntimeException();
  }


  public static void main(String[] args) {}

}
