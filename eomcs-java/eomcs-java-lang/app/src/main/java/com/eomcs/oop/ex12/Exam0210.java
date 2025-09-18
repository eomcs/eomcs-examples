// Lambda 문법 - functional interface의 자격
package com.eomcs.oop.ex12;

public class Exam0210 {

  // 추상 메서드가 한 개짜리 인터페이스여야 한다.
  interface Player {
    void play();
  }

  public static void main(String[] args) {

    // 추상 메서드를 한 개만 갖고 있는 인터페이스에 대해
    // 람다 문법으로 익명 클래스를 만들 수 있다.
    Player p = () -> System.out.println("Player...");
    p.play();
  }
}


