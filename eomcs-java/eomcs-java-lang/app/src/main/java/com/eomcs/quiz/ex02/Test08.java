package com.eomcs.quiz.ex02;
// 출처: codefights.com
// 
// 장애물들의 x 좌표(양의 좌표)가 배열로 주어질 때,
// 0부터 오른쪽으로 동일한 거리를 점프하여 장애물들을 피한다면,
// 점프해야 할 최소 거리는 얼마인지 구하라!
// 
// 예) 
//   [5, 3, 6, 7, 9] ==> 4
// 
/*
You are given an array of integers representing coordinates of obstacles 
situated on a straight line.

Assume that you are jumping from the point with coordinate 0 to the right. 
You are allowed only to make jumps of the same length represented 
by some integer.

Find the minimal length of the jump enough to avoid all the obstacles.

Example

for [5, 3, 6, 7, 9] output should be 4

[input] array.integer inputArray
non-empty array of positive integers

[output] integer
the desired length
 */
//
// [시간 복잡도]
// - ?
//
public class Test08 {

  public static void main(String[] args) {
    System.out.println(avoidObstacles(new int[]{5,3,6,7,9,13,11}) == 4);
  }

  static int avoidObstacles(int[] inputArray) {
    // 이 메서드를 완성하시오!
    return 0;
  }
}
