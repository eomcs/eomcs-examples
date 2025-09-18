// inner class 응용 I : 리랙토링
package com.eomcs.oop.ex11.c;

import java.util.ArrayList;
import java.util.List;

public class Exam0713 {

  public static void main(String[] args) {
    Musics3 m1 = new Musics3();
    m1.add("aaa.mp3");
    m1.add("bbb.mp3");
    m1.add("ccc.mp3");

    Musics3 m2 = new Musics3();
    m2.add("xxx.mp3");
    m2.add("yyy.mp3");

    // 바깥 클래스의 인스턴스를 사용하는 inner 클래스라면
    // inner 클래스의 객체를 만드는 역할도
    // 바깥 클래스가 하는게 유지보수에 더 낫다.
    // => GRASP 설계 기법에서 "정보를 가진자가 그 일을 하라.(Information Expert)"를 적용.
    Musics3.Player p1 = m1.createPlayer();
    Musics3.Player p2 = m2.createPlayer();

    p1.play();
    p2.play();
  }
}


class Musics3 {

  List<String> songs = new ArrayList<>();

  public void add(final String song) {
    songs.add(song);
  }

  public void delete(final int index) {
    songs.remove(index);
  }

  // inner 클래스의 객체를 생성하는 역할을 바깥 클래스가 맡는다.
  public Player createPlayer() {
    return new Player(); // ==> this.new Player();  // 바깥 클래스의 객체 주소 생략!
  }

  class Player {
    public void play() {
      for (final String song : Musics3.this.songs) {
        System.out.println(song);
      }
      System.out.println("-----------------------------");
    }
  }


}


