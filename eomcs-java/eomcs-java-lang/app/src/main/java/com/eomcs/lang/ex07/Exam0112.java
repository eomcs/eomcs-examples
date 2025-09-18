package com.eomcs.lang.ex07;

import java.util.Scanner;

// 1단계: 공백 출력 코드를 메서드로 추출하기
// 2단계: 별을 출력하는 코드를 메서드로 추출하기
//
public class Exam0112 {

  static void printSpaces(int len) {
    int spaceCnt = 1;
    while (spaceCnt <= len) {
      System.out.print(" ");
      spaceCnt++;
    }
  }

  static void printStars(int len) {
    int starCnt = 1;
    while (starCnt <= len) {
      System.out.print("*");
      starCnt++;
    }
  }

  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);
    System.out.print("밑변의 길이? ");
    int len = keyScan.nextInt();
    keyScan.close();

    int starLen = 1;
    while (starLen <= len) {
      printSpaces((len - starLen) / 2);

      printStars(starLen);

      // 출력 줄 바꾸기
      System.out.println();
      starLen += 2;
    }
  }
}