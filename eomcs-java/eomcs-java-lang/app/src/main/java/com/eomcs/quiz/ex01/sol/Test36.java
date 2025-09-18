package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 배열이 주어질 때 다음 조건을 만족하는 경우의 수를 찾으시오.
// => index1 < index2, array[index1] > array[index2]
//
// 형식:
//   countInversionsNaive(정수 배열)
// 예) 
//   countInversionsNaive(new int[] {1, 3, 2, 0}) ==> 4
//
/*
Two elements of the array of integers form an inversion if array[index1] > array[index2] and index1 < index2.

  Given an array of integers, find the number of inversions it contains.

  Example

  for [1, 3, 2, 0] output should be 4

  [input] array.integer inputArray

  [output] integer
 */
//
// [시간 복잡도]
// - O(n!): n은 배열의 개수이다.
//
public class Test36 {

  public static void main(String[] args) {
    System.out.println(countInversionsNaive(new int[] {1, 3, 2, 0}) == 4);
  }

  static int countInversionsNaive(int[] inputArray) {

    int result = 0;

    for (int i = 0; i < inputArray.length; i++) {
      for (int j = i + 1; j < inputArray.length; j++) {
        if (inputArray[i] > inputArray[j]) {
          result++;
        }
      }
    }
    return result;
  }
}
