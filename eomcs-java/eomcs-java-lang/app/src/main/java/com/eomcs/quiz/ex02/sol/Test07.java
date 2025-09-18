package com.eomcs.quiz.ex02.sol;
// 출처: codefights.com
// 
// 주어진 양의 정수 배열에서 가장 큰 짝수를 찾아라!
// 최소 한 개의 짝수가 존재한다.
// 예) 
//   maximalEven([4, 3, 6, 8, 2, 4]) = 8
//
/*
Pick-out the largest even number from an array of integers

Example

maximalEven([4, 3, 6, 8, 2, 4]) = 8

[input] array.integer inputArray
an array of positive integers, it's guaranteed 
that inputArray contains at least one even integer

[output] integer
maximal even element of inputArray
 */
//
// [시간 복잡도]
// - O(n) : n은 배열의 개수이다.
//
public class Test07 {

  public static void main(String[] args) {
    System.out.println(maximalEven(new int[]{4, 3, 6, 8, 2, 4}) == 8);
  }

  static int maximalEven(int[] inputArray) {
    int answer = 0;
    for (int i = 1; i < inputArray.length; i++) {
      if (inputArray[i] % 2 == 0 && inputArray[i] > answer) {
        answer = inputArray[i];
      }
    }
    return answer;
  }
}


