package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 문자열에 포함된 숫자의 합을 구하라.
// 예) 
//   sumUpNumbers("2 apples, 12 oranges") ==> 5
//
/*
Find the sum of all digits that occur in a string.

Example

sumUpNumbers("2 apples, 12 oranges") = 5

[input] string inputString

[output] integer
 */
//
// [시간 복잡도]
// - O(n) : n은 문자열의 길이 
//
public class Test23 {

  public static void main(String[] args) {
    System.out.println(sumUpDigits("2 apples, 12 oranges") == 5);
  }

  static int sumUpDigits(String inputString) {

    int answer = 0;

    for (int i = 0; i < inputString.length(); i++) {
      if ('1' <= inputString.charAt(i) && inputString.charAt(i) <= '9') {
        answer += inputString.charAt(i) - '0';
      }
    }

    return answer;
  }
}
