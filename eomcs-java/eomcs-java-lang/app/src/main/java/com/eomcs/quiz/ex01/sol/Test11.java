package com.eomcs.quiz.ex01.sol;

// 출처: codefights.com
//
// 소문자 알파벳의 문자열이 주어질 때, 서로 다른 알파벳의 개수는?
//
// 예) "cabca" ==> 3
//
/*
Given a string, find the number of different characters in it.

for "cabca" output should be 3

[input] string s

a string of lowercase latin letters
[output] integer
 */
//
// [시간 복잡도]
// - O(26n) = O(n) : n은 문자열의 개수
//
public class Test11 {

  public static void main(String[] args) {
    System.out.println(differentSymbolsNaive("cabca") == 3);
  }

  static int differentSymbolsNaive(String s) {

    int result = 0;

    for (int i = 0; i < 26; i++) {
      boolean found = false;
      for (int j = 0; j < s.length(); j++) {
        if (s.charAt(j) == 'a' + i) {
          found = true;
          break;
        }
      }
      if (found) {
        result++;
      }
    }
    return result;
  }
}
