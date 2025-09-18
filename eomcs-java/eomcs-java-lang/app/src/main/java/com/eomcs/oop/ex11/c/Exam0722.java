// inner class 응용 II : inner 클래스와 인터페이스
package com.eomcs.oop.ex11.c;

import java.util.ArrayList;
import java.util.List;

public class Exam0722 {

  public static void main(String[] args) {
    Musics6 m1 = new Musics6();
    m1.add("aaa.mp3");
    m1.add("bbb.mp3");
    m1.add("ccc.mp3");

    Musics6 m2 = new Musics6();
    m2.add("xxx.mp3");
    m2.add("yyy.mp3");

    Player p1 = m1.createPlayer();
    Player p2 = m2.createPlayer();

    p1.play();
    p2.play();
  }
}


class Musics6 {

  List<String> songs = new ArrayList<>();

  public void add(final String song) {
    songs.add(song);
  }

  public void delete(final int index) {
    songs.remove(index);
  }


  public Player createPlayer() {

    // lambda 문법 사용하여 인터페이스 구현
    return () -> {
      for (final String song : Musics6.this.songs) {
        System.out.println(song);
      }
      System.out.println("-----------------------------");
    };
  }

}


