package com.eomcs.lang.ex07;

import java.util.Scanner;

//# 메서드 : 사용 후
//
public class Exam0120 {

  // 스페이스를 출력하는 코드들을 
  // 관리하기 쉽도록 별도의 블록에 모아 놓는다.
  // 그리고 그 블록에 대해 이름을 붙인다.
  // => 이렇게 정의한 블록을 "메서드(method)" 또는
  //    "함수(function)"이라 부른다.
  // => 자바는 "함수" 보다는 주로 "메서드"라는 이름으로 부른다.
  // => 메서드 이름은 명령 형태의 동사구로 짓는다.
  //    예) getName(), setName(), printName(), doFilter(), parseInt() 등
  // => 물론 명사구나 전치사구 형태로 짓는 경우도 있다.
  //    예) valueOf(), toString() 등
  //
  static void printSpaces(int len) {
    for (int i = 0; i < len; i++) {
      System.out.print(" ");
    }
  }

  // '*' 문자를 출력하는 코드를 관리하기 쉽게 별도의 블록으로 빼둔다.
  // 그리고 그 블록의 이름을 붙인다.
  // 이렇게 별도로 빼둔 코드 블록에 이름을 붙인 것을 "메서드=함수"라고 부른다.
  //
  static void printStars(int len) {
    for (int i = 0; i < len; i++) {
      System.out.print("*");
    }
  }

  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);
    System.out.print("밑변의 길이? ");
    int len = keyScan.nextInt();
    keyScan.close();

    for (int starLen = 1; starLen <= len; starLen += 2) {
      // 명령 코드들을 기능 별로 묶어 놓고
      // 필요할 때마다 다음과 같이 사용하면 
      // 코드를 읽기가 쉬워진다.
      printSpaces((len - starLen) / 2);
      printStars(starLen);
      System.out.println();
    }
  }
}




