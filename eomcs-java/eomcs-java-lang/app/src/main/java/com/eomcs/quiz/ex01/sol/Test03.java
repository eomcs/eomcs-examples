package com.eomcs.quiz.ex01.sol;

// [문제] 
// 두 위치의 비트 값을 맞교환 하라! 
// 예) 값:   0b00101100_01110001
//     2번째(2^2 자리) 비트와 13번째(2^13 자리) 비트
//     결과: 0b00001100_01110101
//  
// [훈련 목표]
// - 관계 연산자 및 비트 연산자, 비트 이동 연산자 활용
// - 반복문 활용
// - 메서드 파라미터 및 리턴 값 다루기
//
// [시간 복잡도]
// - O(1) : 시간 복잡도는 입력 크기와 상관없다.
//
public class Test03 {

  public static void main(String[] args) {
    int r = swapBits(0b00101100_01110001, 2, 13);
    System.out.println(r == 0b00001100_01110101); // true

    r = swapBits(0b01010111_01100011, 6, 10);
    System.out.println(r == 0b01010111_01100011); // true
  }

  static int swapBits(int value, int i, int j) { 
    if (((value >>> i) & 1) != ((value >>> j) & 1)) {
      int bitMask = (1 << i) | (1 << j);
      value ^= bitMask;  
    }
    return value;
  }

}
