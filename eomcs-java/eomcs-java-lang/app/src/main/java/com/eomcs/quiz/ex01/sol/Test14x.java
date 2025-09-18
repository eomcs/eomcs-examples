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
public class Test14x {
  public static void main(String[] args) {
    System.out.println(numberReverse(123) == 321);
    System.out.println(numberReverse(120) == 21);
  }

  static int numberReverse(int input) {
    int reversed = 0;
    while (input > 0) {
      int v = input % 10; // 입력 값에서 1의 자리 값을 알아낸다.
      reversed = (reversed * 10) + v; // 기존의 값을 한 자리 올린 후, 알아 낸 1의 자리 값을 결과 변수에 더한다.
      input /= 10; // 입력 값을 한 자리 낮춘다.
    }
    return reversed;
  }
}
