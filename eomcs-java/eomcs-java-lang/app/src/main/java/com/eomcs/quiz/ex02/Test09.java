package com.eomcs.quiz.ex02;

// copyright by codefights.com
// 
// 주어진 배열을 꺼꾸로 나열하라!
// 예) 
//    myReverse([1, 3, 2]) = [2, 3, 1]
/*
Reverse an array

Example

myReverse([1, 3, 2]) = [2, 3, 1]

[input] array.integer input

[output] array.integer
 */
//
// [시간 복잡도]
// - ? 
//
public class Test09 {

  public static void main(String[] args) {
    int[] reverseList = myReverse(new int[]{9,8,7,6,5,4,3,2,1});
    for (int i : reverseList) {
      System.out.print(i + ",");
    }
  }

  static int[] myReverse(int[] input) {
    // 이 메서드를 완성하시오!
    return input;
  }
}
