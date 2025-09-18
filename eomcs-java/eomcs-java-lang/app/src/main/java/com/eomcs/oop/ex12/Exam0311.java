// 아규먼트에 람다(lambda) 활용
package com.eomcs.oop.ex12;

public class Exam0311 {

  static interface Player {
    void play();
  }

  static void testPlayer(Player player) {
    player.play();
  }

  public static void main(String[] args) {
    // 간단한 인터페이스 구현체 조차
    // 다음과 같이 따로 정의하여 사용하면 매우 불편하다.
    Player player = new Player() {
      @Override
      public void play() {
        System.out.println("실행~~~~");
      }
    };

    testPlayer(player);
  }

}


