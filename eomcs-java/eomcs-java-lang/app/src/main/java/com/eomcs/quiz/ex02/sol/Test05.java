package com.eomcs.quiz.ex02.sol;

// 출처: codefights.com
// 분수 배열에서 가장 큰 분수의 인덱스를 찾아라!
// 예) 분자: 5, 2, 5
//     분모: 6, 3, 4
// 위 예에서 가장 큰 분모는 5/4를 가리키는 인덱스 2이다.
// 

/* 원문
Given a list of fractions find the largest one.

Example

for numerators=[5, 2, 5], denominators=[6, 3, 4] output 
should be 2 since 5/4 is the largest fraction

[input] array.integer numerators

array of integers representing numerators of the fractions
[input] array.integer denominators

array of integers of the same length as numerators representing denominators 
where the i-th fraction equals to numerators[i]/denominators[i]
[output] integer

index of the largest fraction assuming that none of the fractions are equal
 */
//
// [시간 복잡도]
// - O(n) : n은 배열의 길이
//
public class Test05 {
  public static void main(String[] args) {
    System.out.println(maxFraction(
        new int[]{5, 2, 5}, 
        new int[]{6, 3, 4}) == 2);

    System.out.println(maxFraction(
        new int[]{2, 4, 5, 16, 56}, 
        new int[]{3, 5, 6, 22, 99}) == 2);

  }

  static int maxFraction(int[] numerators, int[] denominators) {
    int maxFractionIndex = 0;
    for (int i = 1; i < numerators.length; i++) {
      if (numerators[i] * denominators[maxFractionIndex] >
      denominators[i] * numerators[maxFractionIndex]) {
        maxFractionIndex = i;
      }
    }
    return maxFractionIndex;
  }
}
