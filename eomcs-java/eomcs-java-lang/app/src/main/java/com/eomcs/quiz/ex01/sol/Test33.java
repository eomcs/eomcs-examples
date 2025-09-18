package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 사칙연산(+, -, *, /)만 이용하여 나눈 나머지를 구하라!
//
// 형식:
//   findTheRemainder(값, 나누는값)
// 예) 
//   findTheRemainder(14, 3) ==> 2
//
/*
Given two integers a and b, find the remainder of a when divided by b.

Example

for a=5, b=3 output should be 2

[input] integer a
a positive integer

[input] integer b
a positive integer

[output] integer
 */
//
// [시간 복잡도]
// - O(n): 값을 나누는수로 뺀 회수
//
public class Test33 {

  public static void main(String[] args) {
    System.out.println(findTheRemainder(14, 3) == 2);
  }

  static int findTheRemainder(int a, int b) {
    while (a >= b) {
      a -= b;
    }
    return a;
  }
}
