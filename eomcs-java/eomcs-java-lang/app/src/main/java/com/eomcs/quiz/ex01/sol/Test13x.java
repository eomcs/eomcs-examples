package com.eomcs.quiz.ex01.sol;

//출처: codefights.com
//
// k의 배수 중에서 n보다 크지 않은 값을 모두 더한 결과는 무엇인가?
// 조건:
//   n과 k는 양의 정수이다.
//
// 예) sumOfMultiples(7, 2) = 2 + 4 + 6 = 12
//   
//
/*
For given n and k find the sum of all multiples of k 
that are not greater than n.

Example

sumOfMultiples(7, 2) = 2 + 4 + 6 = 12

[input] integer n
positive integer

[input] integer k
positive integer

[output] integer
 */
//
// [시간 복잡도]
// - O(n/k) = O(n) : n은 k의 배수들의 개수이다.
//
public class Test13x {
  public static void main(String[] args) {
    System.out.println(sumOfMultiples(7, 2) == 12);
  }

  static int sumOfMultiples(int n, int k) {
    int result = 0;

    for (int i = k; i <= n; i += k) { // k의 배수
      result += i;
    }

    return result;
  }
}
