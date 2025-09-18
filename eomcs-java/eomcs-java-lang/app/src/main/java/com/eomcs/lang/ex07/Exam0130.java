package com.eomcs.lang.ex07;

import java.util.Scanner;

//# 메서드 : 리팩토링
//
public class Exam0130 {

  public static void printSpaces(int len) {
    for (int i = 0; i < len; i++) {
      System.out.print(" ");
    }
  }

  public static void printStars(int len) {
    for (int i = 0; i < len; i++) {
      System.out.print("*");
    }
  }

  // 코드를 유지보수하기 쉽도록 가능한 기능 별로 묶어 둔다.
  // 그래서 Exam0120에 있던 코드 중에서 공백을 계산하는 코드를 
  // 별도의 블록으로 분리하여 이름을 부여한다.
  public static int getSpaceLength(int totalStar, int displayStar) {
    return (totalStar - displayStar) / 2;
  }

  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);
    System.out.print("밑변의 길이? ");
    int len = keyScan.nextInt();

    for (int starLen = 1; starLen <= len; starLen += 2) {
      // 출력할 스페이스의 개수를 계산하는 코드를 
      // 블록에 묶어 놓고 이름을 부여해두고 사용하면
      // 코드를 이해하기가 더 쉽다.
      printSpaces(getSpaceLength(len, starLen));
      printStars(starLen);
      System.out.println();
    }
    keyScan.close();
  }
}