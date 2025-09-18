package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 산술식 A # B = C 가 있을 때,
// # 대신에 +, -, *, / 중 한 개의 연산자를 넣을 수 있는지 검사하라!
// 
//
// 형식:
//   arithmeticExpression(A, B, C)
// 예) 
//   arithmeticExpression(0, 1, 2) ==> false
//   arithmeticExpression(7, 3, 4) ==> true
//
/*
Consider an arithmetic expression of the form A#B=C.
 Check whether it is possible to replace # with one of the four signs: 
 +, -, * or / to obtain a correct expression.

      [input] integer A

      [input] integer B

      [input] integer C

      [output] boolean

      true if the desired replacement is possible, false otherwise
 */
//
// [시간 복잡도]
// - O(1): 시간 복잡도는 일정하다.
//
public class Test37 {

  public static void main(String[] args) {
    System.out.println(arithmeticExpression(0, 1, 2) == false);
    System.out.println(arithmeticExpression(7, 3, 4) == true);
  }

  static boolean arithmeticExpression(int A, int B, int C) {

    if (A + B == C || A * B == C || A / B == C || A - B == C) {
      return true;
    }
    return false;
  }
}
