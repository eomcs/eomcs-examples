package com.eomcs.oop.ex01;

// # public 클래스와 기본 클래스(default class = package private class)
//
public class Exam0610 {
  public static void main(String[] args) {

    // 같은 패키지에 소속된 클래스 사용
    // - 공개, 비공개 상관없이 사용할 수 있다.
    com.eomcs.oop.ex01.X obj1;
    com.eomcs.oop.ex01.Y obj2;


    // 다른 패키지에 소속된 클래스 사용
    //
    // - X2 클래스는 public 이 아니다.
    // - 따라서 다른 패키지의 클래스는 X2를 사용할 수 없다.
    //    com.eomcs.oop.ex01.sub.X2 obj3; // 컴파일 오류!

    // - Y2 클래스는 public으로 공개된 클래스이다.
    com.eomcs.oop.ex01.sub.Y2 obj4;
  }
}
