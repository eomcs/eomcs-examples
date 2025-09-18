package com.eomcs.quiz.ex01.sol;

// [문제] 
// 양의 정수 x와 y를 나눈 몫을 구하라.
// 단, 덧셈과 뺄셈, 비트 이동 연산자만 이용해야 한다.
// 예) 
//     divide(57, 7) ==> 11
//
// [시간 복잡도]
// - O(n) : y가 1일 때 최대 n 번 반복한다.
//
public class Testxxa {

  public static void main(String[] args) {
    System.out.println(divide(57, 5) == 11); // true
  }

  static int divide(int x, int y) { 
    int value = 0;
    while (x > y) {
      value++;
      x -= y;
    }
    return value;
  }

}
