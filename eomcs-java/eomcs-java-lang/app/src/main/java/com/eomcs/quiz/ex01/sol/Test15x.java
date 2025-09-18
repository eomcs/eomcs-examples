package com.eomcs.quiz.ex01.sol;
// 출처: codefights.com
// 
// 주어진 문자열이 반복 문자열인지 검사하라!
// 예) 
//   "abcdeabcde"   --> true
//   "qqq"          --> false
//   "2w2www        --> false
//
/*
Determine whether the given string is a concatenation of another string 
to itself.

Example

for 'tandemtandem' output should be true

for 'qqq' or '2w2ww' output should be false

[input] string inputString

[output] boolean

true if inputString represents a string concatenated to itself, 
false otherwise

 */
//
// [시간 복잡도]
// - O(n/2) = O(n) : n은 문자열의 개수
//
public class Test15x {

  public static void main(String[] args) {
    System.out.println(isTandemRepeat("abcdeabcde")); // true
    System.out.println(isTandemRepeat("qqq")); // false
    System.out.println(isTandemRepeat("2w2www")); // false
  }

  static boolean isTandemRepeat(String inputString) {
    // 문자열의 개수가 홀수이면 반복한 문자열이 아니다.
    if (inputString.length() % 2 != 0) {
      return false;
    }

    int half = inputString.length() / 2; // 문자열의 절반 길이 
    for (int i = 0; i < half; i++) {
      // 문자열을 절반으로 잘라서 순서대로 비교한다.
      if (inputString.charAt(i) != inputString.charAt(i + half)) {
        return false;
      }
    }
    return true;
  }
}
