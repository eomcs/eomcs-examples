// 아규먼트에 람다(lambda) 활용
package com.eomcs.oop.ex12;

public class Exam0310 {

  interface Player {
    void play();
  }

  static void testPlayer(Player p) {
    p.play();
  }

  public static void main(String[] args) {
    // 로컬 클래스
    class MyPlayer implements Player {
      @Override
      public void play() {
        System.out.println("실행!");
      }
    }

    testPlayer(new MyPlayer());

  }
}


