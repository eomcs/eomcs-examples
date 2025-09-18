package com.eomcs.quiz.ex01;

// 출처: codefights.com
// 산술 진행의 처음 두 값에 따라 n 번째 항목의 값은 무엇인가?
//   arithmeticProgression(e1, e2, n)
//     e1: 첫 번째 항목
//     e2: 두 번째 항목
//     n:  값을 알아낼 항목의 순서
// 예) 
//   arithmeticProgression(2, 5, 5) ==> 14
//
/*
Given first two elements of an arithmetic progression, find its n-th element.

[input] integer element1
1st element of the arithmetic progression

[input] integer element2
2nd element of the arithmetic progression

[input] integer n
positive integer

[output] integer

n-th element of the arithmetic progression
 */
//
// [시간 복잡도]
// - O(1) : 일정하다.
//
public class Test09 {
  public static void main(String[] args) {
    System.out.println(arithmeticProgression(2, 5, 5) == 14); // 2, 5, 8, 11, 14, 17
    System.out.println(arithmeticProgression(2, 7, 5) == 22); // 2, 7, 12, 17, 22, 27
  }

  static int arithmeticProgression(int element1, int element2, int n) {
    // 이 메서드를 완성하시오!
    return 0; 
  }
}



