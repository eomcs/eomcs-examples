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
public class Test11x {

  public static void main(String[] args) {
    System.out.println(differentSymbolsNaive("cabca") == 3);
  }

  static int differentSymbolsNaive(String s) {
    int result = 0;
    // 이 메서드를 완성하시오!
    // 알파벳 a에서 z까지 반복한다.
    for (int i = 'a'; i <= 'z'; i++) {
      // 문자 코드 값과 문자를 출력해 본다.
      //System.out.printf("%1$x ==> %1$c\n", i);

      // 파라미터로 주어진 문자열에서 문자를 반복해서 꺼낸다.
      for (int x = 0; x < s.length(); x++) {
        char c = s.charAt(x); // 문자열에서 인덱스 x의 문자를 꺼낸다. 
        if (c == i) { // 알파벳의 코드 값과 문자의 코드 값이 같다면
          result++; // 개수를 한 개 증가시킨다.
          break; // 다음 항목은 검사할 필요가 없다. 왜? 중복 카운트는 하지 않는다.
        }
      }
    }
    return result;
  }
}
