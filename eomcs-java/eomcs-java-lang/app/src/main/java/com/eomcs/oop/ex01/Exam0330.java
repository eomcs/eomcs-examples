package com.eomcs.oop.ex01;

//# 레퍼런스와 인스턴스 - 레퍼런스 카운트와 가비지
//
public class Exam0330 {
  public static void main(String[] args) {
    class Score {
      String name;
      int kor;
      int eng;
      int math;
      int sum;
      float aver;
    }

    Score s1 = new Score();
    Score s2 = new Score();

    s2 = s1;
    // s1의 주소는 s2에도 저장되었다.
    // 즉 s1이 가리키는 객체는 s2도 가리키게 되었다.
    // JVM은 객체의 참조 상태를 관리하기 위해 "레퍼런스 카운트(reference count)"를 이용한다.
    // s1이 가리키는 객체처럼 주소를 알고 있는 변수가 늘어나면 레퍼런스 카운트를 증가시키고
    // s2이 이전에 가리켰던 객체처럼 주소를 알고 있는 변수가 줄어들면 레퍼런스 카운트를 감소시킨다.
    // 레퍼런스 카운트가 0인 상태가 "가비지(garbage)"이다.

  }
}

