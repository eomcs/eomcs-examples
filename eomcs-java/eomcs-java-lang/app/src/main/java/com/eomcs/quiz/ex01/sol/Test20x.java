package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 양의 정수가 주어질 때 가장 큰 숫자를 찾아라!
// 예)
//    maxDigit(2542) ==> 5
//  
//
/*
Find the largest digit of the given number.

[input] integer n
non-negative integer

[output] integer
largest digit of n
 */
//
// [시간 복잡도]
// - O(10) = O(1) : int 값 최대 자릿수이다.  
//
public class Test20x {

  public static void main(String[] args) {
    System.out.println(maxDigit(736) == 7);
    System.out.println(maxDigit(2542) == 5);
  }

  static int maxDigit(int n) {
    int result = 0;
    while (n > 0) {
      if ((n % 10) > result) {
        result = n % 10;
      }
      n /= 10;
    }
    return result;
  }
}
