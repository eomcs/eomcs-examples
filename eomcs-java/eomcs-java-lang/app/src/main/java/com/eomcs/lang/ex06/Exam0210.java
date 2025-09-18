package com.eomcs.lang.ex06;

import java.util.Scanner;

//# 흐름 제어문 - switch 사용 전
//
public class Exam0210 {
  public static void main(String[] args) {

    // 키보드 입력을 다루는 도구 생성
    Scanner keyScan = new Scanner(System.in);

    System.out.println("[지원부서]");
    System.out.println("1. S/W개발");
    System.out.println("2. 일반관리");
    System.out.println("3. 시설경비");
    System.out.print("지원 분야의 번호를 입력하세요? ");

    // 사용자가 콘솔 창에 입력할 수 있도록 
    // 커서를 대기시키고 싶다면 nextInt()를 실행하라!
    // => nextInt()는 사용자가 숫자를 입력하고 엔터를 칠 때까지 기다린다.
    int no = keyScan.nextInt();

    System.out.println("제출하실 서류는 다음과 같습니다.");
    if (no == 1) {
      System.out.println("정보처리자격증");
      System.out.println("졸업증명서");
      System.out.println("이력서");
    } else if (no == 2) {
      System.out.println("졸업증명서");
      System.out.println("이력서");
    } else if (no == 3) {
      System.out.println("이력서");
    } else {
      System.out.println("올바른 번호를 입력하세요!");
    }

    keyScan.close();
  }
}

