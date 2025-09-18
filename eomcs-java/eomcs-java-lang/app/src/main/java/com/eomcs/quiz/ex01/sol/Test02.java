package com.eomcs.quiz.ex01.sol;

// [문제] 
// 패리티(parity)를 구하라!
// - 1의 개수가 홀수 개이면 1
// - 1의 개수가 짝수 개이면 0
// [훈련 목표]
// - 관계 연산자 및 비트 연산자, 비트 이동 연산자 활용
// - 반복문 활용
// - 메서드 파라미터 및 리턴 값 다루기
// [시간 복잡도]
// - O(n), n은 비트 개수
public class Test02 {

  public static void main(String[] args) {
    int p = parity(0b01100011);
    System.out.println(p == 0); // true

    System.out.println("------------------------");

    p = parity(0b01010111_01100011);
    System.out.println(p == 1); // true
  }

  static int parity(int value) {
    int r = 0;

    while (value != 0) {
      r ^= (value & 1);
      value >>>= 1;
      System.out.println("==>");
    }

    return r;
  }

}
