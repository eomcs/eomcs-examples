// Lambda 문법 - 람다 파라미터 I
package com.eomcs.oop.ex12;

public class Exam0130 {

  interface Player {
    void play(String name);
  }

  public static void main(String[] args) {
    // 0) 람다 표현으로 인터페이스 구현
    Player p0 = (String name) -> {
      System.out.println(name + " 님 환영합니다.");
    };
    p0.play("홍길동");

    // 1) 메서드 body에 문장이 한 개만 있을 경우 중괄호 생략가능
    //    또한 세미콜론 제거.
    Player p1 = (String name) -> System.out.println(name + " 님 환영합니다.");
    p1.play("홍길동");

    // 2) 파라미터 타입을 생략할 수 있다.
    Player p2 = (name) -> System.out.println(name + " 님 환영합니다.");
    p2.play("홍길동");

    // 3) 파라미터가 한 개일 때는 괄호도 생략할 수 있다.
    Player p3 = name -> System.out.println(name + " 님 환영합니다.");
    p3.play("홍길동");
  }
}


