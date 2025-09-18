package com.eomcs.quiz.ex01;

// 출처: codefights.com
//
// n!(n 팩토리얼) 값을 조사하여 몇 개의 0으로 끝나는 지(trailing zeros) 개수를 리턴하라.
// 예1) 10!의 값은 두 개의 0으로 끝난다.
// 예2) 23!은 4개의 값으로 끝난다. (23!의 값은 4바이트를 초과하기 때문에 테스트하지 말아라!)
//
/* 원문
Given an integer n, find the number of trailing zeros 
in the decimal representation of n! 
(the exclamation mark means factorial).

Example

for n = 10 output should be 2

[input] integer n

a positive integer
[output] integer
 */
//
// [시간 복잡도]
// - ?
//
public class Test07 {
  public static void main(String[] args) {
    System.out.println(factorialTrailingZeros(1) == 0);
    System.out.println(factorialTrailingZeros(2) == 0);
    System.out.println(factorialTrailingZeros(3) == 0);
    System.out.println(factorialTrailingZeros(4) == 0);
    System.out.println(factorialTrailingZeros(5) == 1);
    System.out.println(factorialTrailingZeros(10) == 2);
    System.out.println(factorialTrailingZeros(15) == 3);
    System.out.println(factorialTrailingZeros(20) == 4);
    System.out.println(factorialTrailingZeros(25) == 6);
    System.out.println(factorialTrailingZeros(30) == 7);
  }

  static int factorialTrailingZeros(int n) {  
    int result = 0;
    // 이 메서드를 완성하시오!
    return result;
  }
}

// 참고:
// => "Legendre's formula(= de Polignac's formula)" 수학식을 적용해 보라! 
// 

