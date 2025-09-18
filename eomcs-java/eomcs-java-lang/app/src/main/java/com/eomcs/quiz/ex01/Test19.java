package com.eomcs.quiz.ex01;
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
// - ? 
//
public class Test19 {

  public static void main(String[] args) {
    System.out.println( myMaxOfThree(8, 7, 5) == 8); 
    System.out.println( myMaxOfThree(8, 5, 7) == 8); 
    System.out.println( myMaxOfThree(7, 8, 5) == 8); 
    System.out.println( myMaxOfThree(7, 5, 8) == 8); 
    System.out.println( myMaxOfThree(5, 7, 8) == 8);
  }

  static int myMaxOfThree(int a, int b, int c) {
    // 이 메서드를 완성하시오!
    return c;
  }
}






