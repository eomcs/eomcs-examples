package com.eomcs.quiz.ex01;
// copyright by codefights.com
// 
// 두 개의 정수 L과 R이 주어질 때 다음 조건을 만족하는 (A,B)의 개수를 구하라!
//   L <= A <= R, 
//   L <= B <= R, 
//   A = B = 1 가능
//   A^3 == B^2 이다.
//
// 예) 
//   equationSolutions(1, 4) ==> 1
//   equationSolutions(1, 8) ==> 2
// 
//
/*
Given integers L and R, 
find the number of different pairs of integers A and B 
such that L <= A <= R and L <= B <= R and A^3 = B^2.

Note that A and B may even coincide (A = B = 1 is one of the possibilities).

Example

for L=1, R=4 output should be 1 - (1,1)
for L=1, R=8 output should be 2 - (1,1) and (4,8)

[input] integer L

[input] integer R

[output] integer
 */
//
// [시간 복잡도]
// - ?
//
public class Test24 {

  public static void main(String[] args) {
    System.out.println(equationSolutions(1, 4) == 1);
    System.out.println(equationSolutions(1, 8) == 2);
  }

  static int equationSolutions(int L, int R) {
    int result = 0;
    // 이 메서드를 완성하시오!
    return result;
  }
}
