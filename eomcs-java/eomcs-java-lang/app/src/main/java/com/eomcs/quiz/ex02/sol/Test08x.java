package com.eomcs.quiz.ex02.sol;
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
// - O(kn) = O(n^2) : k는 배열의 개수. n은 배열에서 가장 큰 수.
//
public class Test08x {

  public static void main(String[] args) {
    System.out.println(avoidObstacles(new int[]{5,3,6,7,9,13,11}) == 4);
  }

  static int avoidObstacles(int[] inputArray) {
    // 한 칸부터 점프를 한다.
    for (int jumping = 1; ; jumping++) {

      // 배열에 주어진 장애물들의 좌표 중에서 점프하는 칸 수의 배수가 있으면 안된다.
      for (int i = 0; i < inputArray.length; i++) {
        if (inputArray[i] % jumping == 0) { // 장애물의 좌표가 점프하는 칸 수의 배수라면, 
          break; // 더이상 계산할 필요가 없다. 왜? 점프하다가 걸리는 장애물 좌표다.
        }

        // 장애물의 좌표 중에 점프 칸수의 배수가 없다면 그 점프하는 수가 답이다.
        if (i == inputArray.length - 1) 
          return jumping;
      }
    }
  }
}
