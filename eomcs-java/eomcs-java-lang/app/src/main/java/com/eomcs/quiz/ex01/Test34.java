package com.eomcs.quiz.ex01;
// copyright by codefights.com
// 
// 문자열이 주어질 때, 지정된 인덱스로부터 지정된 개수의 문자열을 추출하라!
//
// 형식:
//   mySubstringByLength(문자열, 문자열을 추출할 시작 인덱스, 추출할 문자열 개수)
//   - 문자열을 추출할 시작 인덱스는 문자열의 길이 보다 작아야 한다.
//   - 추출할 문자열의 길이는 유효해야 한다.
// 예) 
//   mySubstringByLength("abcde", 2, 3) ==> "cde"
//
/*
Implement an algorithm that can output a substring of the given string 
given a starting point and a length.

Example

mySubstring('abcde', 2, 3) = 'cde'
[input] string inputString

[input] integer start
start is less than the length of string

[input] integer length
the length of the substring. It's guaranteed that for a given start, 
length is such that the substring exist 

[output] string
substring of string starting from the start character of inputString 
with length equal to length
 */
//
// [시간 복잡도]
// - ?
//
public class Test34 {

  public static void main(String[] args) {
    System.out.println(mySubstringByLength("abcde", 2, 3).equals("cde"));
  }

  static String mySubstringByLength(String inputString, int start, int length) {
    // 이 메서드를 완성하시오!
    return "";
  }
}






