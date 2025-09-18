package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 세 개의 수 중에서 가장 큰 수를 찾으시오!
//
/*
Find the largest number from the given three integers

[input] integer a

[input] integer b

[input] integer c

[output] integer

max of a,b and c
 */
//
// [시간 복잡도]
// - O(1) : 시간 복잡도는 일정하다. 
//
public class Test19x {

  public static void main(String[] args) {
    System.out.println( myMaxOfThree(8, 7, 5) == 8); 
    System.out.println( myMaxOfThree(8, 5, 7) == 8); 
    System.out.println( myMaxOfThree(7, 8, 5) == 8); 
    System.out.println( myMaxOfThree(7, 5, 8) == 8); 
    System.out.println( myMaxOfThree(5, 7, 8) == 8);
  }

  static int myMaxOfThree(int a, int b, int c) {
    if (a > b) {
      if (a > c) {
        return a;
      }
    } else {
      if (b > c) {
        return b;
      }
    }
    return c;
  }
}
