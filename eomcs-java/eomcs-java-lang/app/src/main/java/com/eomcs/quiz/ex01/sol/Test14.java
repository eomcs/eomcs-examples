package com.eomcs.quiz.ex01.sol;

// 출처: codefights.com
//
// 양의 정수 10진수를 꺼꾸로 만들어라.
// 
// 예) numberReverse(123) = 321
//     numberReverse(120) = 21
// 
/*
Reverse a given integer by rearranging its digits.

Example

numberReverse(123) = 321
numberReverse(120) = 21

[input] integer input
positive integer

[output] integer
 */
//
// [시간 복잡도]
// - O(n) : n은 10진수의 자릿수
//
public class Test14 {
  public static void main(String[] args) {
    System.out.println(numberReverse(123) == 321);
    System.out.println(numberReverse(120) == 21);
  }

  static int numberReverse(int input) {
    int reversed = 0;
    while (input > 0) {
      reversed = reversed * 10 + input % 10;
      input /= 10;
    }
    return reversed;
  }
}
