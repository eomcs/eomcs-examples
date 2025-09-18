package com.eomcs.lang.ex99;

//# 키보드 입력 받기 - int, float, boolean 값 읽기
//
public class Exam0230 {
  public static void main(String[] args) { 
    java.util.Scanner keyScan = new java.util.Scanner(System.in);

    System.out.print("int: ");
    int i = keyScan.nextInt();

    // nextInt()는 한 개의 토큰(token)을 읽을 때가지 기다린다.
    // 한 개의 token을 읽으면 4바이트 정수 값으로 바꾼 다음에 리턴한다.
    // 토큰(token)?
    // => 토큰이란 공백으로 구분되는 단어를 뜻한다.
    // 공백(whitespace)?
    // => 스페이스(space), 탭, 줄바꿈 코드를 말한다.
    // 예) aaa    bbb cc ==> aaa, bbb, cc
    // 중간에 여러 개의 공백이 들어가더라도 한 개의 공백으로 간주한다.

    System.out.print("float: ");
    float f = keyScan.nextFloat();

    System.out.print("boolean: ");
    boolean b = keyScan.nextBoolean();

    keyScan.close();

    System.out.printf("%d, %f, %b\n", i, f, b);
  }
}















