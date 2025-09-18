package com.eomcs.lang.ex07;

import java.util.Scanner;

// 1단계: 공백 출력 코드를 메서드로 추출하기
// 2단계: 별을 출력하는 코드를 메서드로 추출하기
// 3단계: while 대신 for 문 사용하기
// 
public class Exam0113 {

  static void printSpaces(int len) {
    for (int i = 0; i < len; i++) {
      System.out.print(" ");
    }
  }

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
      printSpaces((len - starLen) / 2);
      printStars(starLen);
      System.out.println();
    }
  }
}




