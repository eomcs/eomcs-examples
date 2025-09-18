package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 정수의 자릿수가 주어질 때,
// 앞자리가 0이 아닌 가장 작은 정수의 값을 구하라.
//
// 예) 
//   smallestNumber(1) ==> 0
//   smallestNumber(2) ==> 10
//   smallestNumber(3) ==> 100
// 
//
/*
The algorithm should return the smallest non-negative integer of N digits length.

Example

smallestNumber(2) = 10

[input] integer n
integer's length, positive integer

[output] integer
 */
//
// [시간 복잡도]
// - O(n): n은 자릿수
//
public class Test25 {

  public static void main(String[] args) {
    System.out.println(smallestNumber(1) == 0);
    System.out.println(smallestNumber(2) == 10);
    System.out.println(smallestNumber(3) == 100);
    System.out.println(smallestNumber(4) == 1000);
  }

  static int smallestNumber(int n) {
    if (n == 1) {
      return 0;
    }

    int res = 1;
    for (int i = 1; i < n; i++) {
      res *= 10;
    }

    return res;
  }
}
