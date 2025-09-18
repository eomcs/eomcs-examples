package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 정수 l과 r 사이의 있는 두 수를 더했을 때 n이 될 수 있는 
// 경우가 모두 몇 가지인지 찾아라!
// 단, n, l, r 은 모두 양의 정수이다.
// 두 수 A, B는 다음 조건을 만족해야 한다.
//   l <= A <= B <= r
//
// 형식)
//   countSumOfTwoRepresentations(n, l, r)
// 예) 
//   countSumOfTwoRepresentations(6, 2, 4) == 2
// 
//
/*
Given integers n, l and r, find the number of ways 
to represent n as a sum of two integers A and B 
such that l <= A <= B <= r.

Example

for n=6, l=2, r=4 output should be 2

[input] integer n
a positive integer

[input] integer l
a positive integer

[input] integer r
a positive integer

[output] integer
 */
//
// [시간 복잡도]
// - O(n^2): n은 두 정수 사이의 정수 개수
//
public class Test26 {

  public static void main(String[] args) {
    System.out.println(countSumOfTwoRepresentations(6, 2, 4) == 2);
    System.out.println(countSumOfTwoRepresentations(6, 1, 5) == 3);
  }

  static int countSumOfTwoRepresentations(int n, int l, int r) {
    int result = 0;

    for (int a = l; a <= r; a++) {
      for (int b = a; b <= r; b++) {
        if (a + b == n) {
          result++;
        }
      }
    }

    return result;
  }
}
