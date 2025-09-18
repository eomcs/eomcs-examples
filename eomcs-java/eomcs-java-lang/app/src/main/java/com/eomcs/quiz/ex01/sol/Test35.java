package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 1에서 n까지의 세제곱의 합을 구하라!
//
// 형식:
//   sumOfCubes(n)
// 예) 
//   sumOfCubes(3) ==> 36
//
/*
Find the sum of cubes of all integer from 1 to and including the given integer n.

Example

sumOfCubes(3) = 36

[input] integer n
positive integer

[output] integer
 */
//
// [시간 복잡도]
// - O(n): 1에서 n까지 반복 횟수
//
public class Test35 {

  public static void main(String[] args) {
    System.out.println(sumOfCubes(3) == 36);
  }

  static int sumOfCubes(int n) {
    int result = 0;
    for (int i = 1; i <= n; i++) {
      result += i * i * i;
    }
    return result;
  }
}
