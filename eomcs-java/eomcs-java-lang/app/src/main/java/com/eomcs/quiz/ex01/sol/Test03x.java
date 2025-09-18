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
public class Test03x {

  public static void main(String[] args) {
    int r = swapBits(0b00101100_01110001, 2, 13);
    System.out.println(r == 0b00001100_01110101); // true

    r = swapBits(0b01010111_01100011, 6, 10);
    System.out.println(r == 0b01010111_01100011); // true
  }

  static int swapBits(int value, int i, int j) { 
    // 해당 위치의 비트 값을 알아낸다.
    int iBit = (value >>> i) & 1;
    int jBit = (value >>> j) & 1;

    // 두 비트의 값이 다를 경우에만 비트 맞교환을 수행한다.
    if (iBit != jBit) {
      // 두 비트의 값만 투과시킬 마스크를 만든다.
      int iMask = 1 << i; // i 번째 비트가 1인 마스크
      int jMask = 1 << j; // j 번째 비트가 1인 마스크
      int bitMask = iMask | jMask; // i 번째와 j 번째가 1인 마스크
      value = value ^ bitMask; // 두 비트를 맞교환하려는 값에 마스크를 XOR 한다.
    }
    return value;
  }

}
