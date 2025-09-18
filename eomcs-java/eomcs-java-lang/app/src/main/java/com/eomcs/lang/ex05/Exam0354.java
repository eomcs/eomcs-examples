package com.eomcs.lang.ex05;

//# 비트 연산자 & 활용: 그림의 한 픽셀에서 빨강 색을 제거하고 싶다.
//
public class Exam0354 {
  public static void main(String[] args) {
    int pixel = 0x003f4478; // 각 바이트의 값이 '00RRGGBB' 이라 가정하다.
    System.out.println(pixel & 0x0000ffff);
    // pixel = 00000000_00111111_01000100_01111000
    //       & 00000000_00000000_11111111_11111111
    //         00000000_00000000_01000100_01111000
  }
}

