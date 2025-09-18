package com.eomcs.quiz.ex01.sol;

// [문제] 
// 음이 아닌 두 정수의 곱셈을 수행하는 프로그램을 작성하라.
// 조건:
// => 2진수로 변환하여 비트 연산자만 이용하여 곱셈을 수행한다.
//
// [훈련 목표]
// - 비트 연산자를 이용한 2진수의 곱셈 수행 방법
//
// [시간 복잡도]
// - O(n) : n <= 비트의 개수
//
public class Test08 {
  public static void main(String[] args) {
    System.out.println(multiply(5, 3) == 15);
    System.out.println(multiply(17, 13) == 221);
  }

  static int multiply(int a, int b) {  
    int result = 0;
    while (a > 0) {
      if ((a & 1) == 1) {
        result |= b;
      }
      b <<= 1;
      a >>>= 1;
    }
    return result;
  }
}


