// Lambda 문법 - 람다 body
package com.eomcs.oop.ex12;

public class Exam0120 {

  // "functional interface" = 추상 메서드가 한 개만 있는 인터페이스
  interface Player {
    void play();
  }

  public static void main(String[] args) {
    // 1) 한 문장일 때는 중괄호를 생략할 수 있다.
    Player p1 = () -> System.out.println("테스트1");
    p1.play();

    // 2) 물론 중괄호를 명확히 적어도 된다.
    Player p2 = () -> {
      System.out.println("테스트2");
    };
    p2.play();

    // => 파라미터가 없다고 괄호를 생략할 수는 없다.
    //    Player p3 = -> System.out.println("테스트3"); // 컴파일 오류!
  }
}


