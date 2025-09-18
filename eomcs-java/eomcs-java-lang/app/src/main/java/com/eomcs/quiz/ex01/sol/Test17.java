package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 주어진 수 보다 큰 수 중에서
// 한 개 이상의 0으로 끝나는 가장 작은 수를 구하라!
// 예)
//    nearestRoundNumber(122) = 130
//
/*
A round number is informally considered to be an integer that ends with one or more zeros.

Find the smallest round number that is not less than a given value.

Example

nearestRoundNumber(122) = 130

[input] integer value

[output] integer
 */
//
// [시간 복잡도]
// - O(10) = O(1) : 시간 복잡도는 일정하다.
//
public class Test17 {

  public static void main(String[] args) {
    System.out.println(nearestRoundNumber(122) == 130);
    System.out.println(nearestRoundNumber(99722) == 99730);
  }

  static int nearestRoundNumber(int value) {
    while (value % 10 != 0) {
      value++;
    }
    return value;
  }
}
