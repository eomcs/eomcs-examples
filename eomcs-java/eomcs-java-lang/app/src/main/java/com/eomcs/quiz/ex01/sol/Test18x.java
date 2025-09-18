package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 주어진 수의 범위 안에서 특정 수의 배수 중에서 가장 큰 값을 구하라!
// 만약 찾지 못했다면 -1을 리턴하라!
// 조건:
//    maxDivisor(int left, int right, int divisor)
//    - left: 왼쪽 수
//    - right: 오른쪽 수. 오른쪽 수는 항상 왼쪽 수 보다 같거나 커야 한다.
//    - divisor: 나누는 수
// 예)
//    maxDivisor(2, 17, 4) => 16
//
/*
Given a number and a range, find the largest integer 
within the given range that's divisible by the given number.

[input] integer left
the left bound of the given range

[input] integer right
the right bound of the given range, left <= right

[input] integer divisor
a non-zero integer

[output] integer

maximal integer in the range [left, right] which is divisible 
by divisor or -1 if there in no such number
 */
//
// [시간 복잡도]
// - O(n) : n은 오른쪽 수 - 왼쪽 수 
//
public class Test18x {

  public static void main(String[] args) {
    System.out.println( maxDivisor(2, 17, 4));
  }

  static int maxDivisor(int left, int right, int divisor) {
    // 큰 수를 찾는 것이기 때문에 반복문을 돌릴 때 큰 수에서 작은 수로 돌린다.
    for (int i = right; i >= left; i--) {

      // 주어진 수가 나누는 수의 배수인지 검사
      if (i % divisor == 0) {
        return i;
      }
    }
    return -1;
  }
}
