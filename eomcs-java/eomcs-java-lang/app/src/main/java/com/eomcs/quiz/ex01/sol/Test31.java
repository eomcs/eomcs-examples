package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 다각형의 내각의 합을 구하라!
//
// 형식:
//   sumOfTheAngles(각의 수)
//   단, 각의 수는 3이상이다.
// 예) 
//   sumOfTheAngles(3) ==> 180
// 
//
/*
Find the sum of the angles of a convex n-gon for a given n.

Example

sumOfTheAngles(3) = 180

[input] integer n
a positive integer greater than or equal to 3

[output] integer
 */
//
// [시간 복잡도]
// - O(1): 시간 복잡도는 일정하다.
//
public class Test31 {

  public static void main(String[] args) {
    System.out.println(sumOfTheAngles(3) == 180);
    System.out.println(sumOfTheAngles(4) == 360);
    System.out.println(sumOfTheAngles(5) == 540);
  }

  static int sumOfTheAngles(int n) {
    // 공식) 다각형 내각의 합 = 180 * (n - 2)
    //
    int result = 180 * n;
    result -= 360;
    return result;
  }
}
