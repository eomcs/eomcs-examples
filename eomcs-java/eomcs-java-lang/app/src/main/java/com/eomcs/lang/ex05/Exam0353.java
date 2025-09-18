package com.eomcs.lang.ex05;

//# 비트 연산자 & 활용: 특정 값을 차단하고 특정 값만 통과시킬 때
//=> 특정 위치의 비트 값만 추출하고 싶을 때 사용할 수 있다.
//
public class Exam0353 {
  public static void main(String[] args) {
    int data = 0b1111_1001_0111_1111;
    System.out.println(Integer.toBinaryString(data & 0b0000_1111_1100_0000));
    //   1111_1001_0111_1111
    // & 0000_1111_1100_0000
    //-----------------------
    //   0000_1001_0100_0000

  }
}

