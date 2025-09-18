package com.eomcs.oop.ex01;

//# 레퍼런스와 인스턴스 - 인스턴스 주소 주고 받기
//
public class Exam0310 {
  public static void main(String[] args) {
    class Score {
      String name;
      int kor;
      int eng;
      int math;
      int sum;
      float aver;
    }

    // Score 레퍼런스 선언 + 인스턴스 생성(사용할 메모리 확보)
    Score s1 = new Score();

    // s1에 저장된 주소를 s2에도 저장한다.
    // => s1과 s2는 같은 메모리를 가리킨다.
    Score s2 = s1;

    s1.name = "홍길동";

    // s1이 가리키는 메모리는 s2를 사용하여 접근할 수 있다.
    System.out.println(s2.name);
  }
}
