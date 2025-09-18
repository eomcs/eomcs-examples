package com.eomcs.quiz.ex01.sol;

// [문제] 
// 양의 정수 x를 2진수로 표현했을 때 1 값을 갖는 비트 개수를 정수의 무게라고 정의하자.
// 같은 무게를 가지는 양의 정수 중에서 x와 가장 가까운 양의 정수를 구하시오!
// 예) x = 0b0000_1010 (10)
//   x와 같은 무게를 가지는 정수들
//     0b0000_1001 (9)
//     0b0000_1100 (12)
//     0b0001_0010 (18)
//     ...
//   답: 0b0000_1001 (9)
//
// [훈련 목표]
// - 2진수를 다루는 방법
// - 연산자, 흐름제어문을 다루는 방법
//
// [시간 복잡도]
// - O(n) : n은 비트의 길이
//
public class Test06 {

  public static void main(String[] args) {
    System.out.println(closestIntSameBit(0b00001010) == 0b00001001); // 10 ==> 9
    System.out.println(closestIntSameBit(0b11001000) == 0b11000100); // 200 ==> 196
  }

  static int closestIntSameBit(int x) {
    for (int i = 0; i < 31; i++) {
      if (((x >>> i) & 1) != ((x >>> (i + 1)) & 1)) {
        x ^= (1 << i) | (1 << (i + 1));
        return x;
      }
    }

    // x의 모든 비트가 0이거나 1이면 오류를 반환한다.
    throw new IllegalArgumentException("모든 비트가 0 또는 1이다.");
  }
}
