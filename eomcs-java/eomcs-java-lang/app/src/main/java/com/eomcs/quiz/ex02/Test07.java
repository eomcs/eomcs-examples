package com.eomcs.quiz.ex02;
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
// - ?
//
public class Test07 {

  public static void main(String[] args) {
    System.out.println(maximalEven(new int[]{9, 3, 6, 7, 2, 4}) == 6);
  }

  static int maximalEven(int[] inputArray) {
    int answer = 0; // 양의 정수만 고려하기에 시작 값을 0으로 설정한다.
    // 이 메서드를 완성하시오!
    return answer;
  }
}


