// Lambda 문법 - functional interface의 자격
package com.eomcs.oop.ex12;

public class Exam0230 {

  // 여러 개의 메서드가 있다 하더라도 추상 메서드가 한 개이면 된다.
  interface Player {

    static String info() {
      return "Player입니다.";
    }

    default void stop() {}

    void play();
  }

  public static void main(String[] args) {
    // static 메서드나 default 메서드가 몇개이든 그 개수는 중요하지 않다.
    // 추상 메서드가 한 개이면 람다 문법을 사용할 수 있다.
    Player p = () -> System.out.println("Player...");
    p.play();
    System.out.println(Player.info());
  }
}


