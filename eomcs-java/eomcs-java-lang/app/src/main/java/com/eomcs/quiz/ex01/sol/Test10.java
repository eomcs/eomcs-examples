package com.eomcs.quiz.ex01.sol;

// 출처: codefights.com
// 
// 원을 이루는 n 개의 숫자가 있다.
// 0 번째 숫자와 n-1 번째 숫자가 서로 이웃하고 있을 때
// 첫 번째 수와 마주하는 수를 알아내시오!
//
// 예) n = 10 : n은 짝수이며, 원을 이루는 숫자의 개수이다.
//     firstNumber = 2: 첫 번째 숫자이다.
//     결과: 7
//    
/*
Consider integer numbers from 0 to n-1 written down along 
the circle in such a way that the distance 
between any two neighbouring numbers is equal 
(note that 0 and n-1 are neighbouring, too).

Given n and firstNumber, find the number which is written 
in the radially opposite position to firstNumber.

Example

for n=10, firstNumber=2 output should be 7

[input] integer n

a positive even integer n
[input] integer firstNumber

an integer from 0 to n-1
[output] integer
 */
//
// [시간 복잡도]
// - O(1) : 계산의 복잡도는 일정하다.
//
public class Test10 {

  public static void main(String[] args) {
    System.out.println(circleOfNumbers(10, 2) == 7); 

  }

  static int circleOfNumbers(int n, int firstNumber) {
    return (firstNumber + n / 2);
  }
}
