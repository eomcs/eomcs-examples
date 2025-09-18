package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 정수의 범위 안에서 주어진 값으로 나눌 때 
// 가장 큰 정수를 알아내라!
// 만약 주어진 값으로 나눌 수 있는 수를 찾지 못했으면 -1을 리턴한다.
//
// 형식:
//   maxDivisor(시작, 끝, 나누는수)
// 예) 
//   maxDivisor(2, 13, 4) ==> 12
// 
//
/*
  Given a number and a range, find the largest integer within the given 
  range that's divisible by the given number.

  [input] integer left
  the left bound of the given range

  [input] integer right
  the right bound of the given range, left <= right

  [input] integer divisor
  a non-zero integer

  [output] integer
  maximal integer in the range [left, right] which is divisible by divisor 
  or -1 if there in no such number
 */
//
// [시간 복잡도]
// - O(n): n은 정수의 범위다.
//
public class Test28 {

  public static void main(String[] args) {
    System.out.println(maxDivisor(2, 13, 4) == 12);
  }

  static int maxDivisor(int left, int right, int divisor) {

    for (int i = right; i >= left; i--) {
      if (i % divisor == 0) {
        return i;
      }
    }
    return -1;
  }
}
