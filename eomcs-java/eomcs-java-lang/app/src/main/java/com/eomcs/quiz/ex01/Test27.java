package com.eomcs.quiz.ex01;
// copyright by codefights.com
// 
// 양의 정수 배열에서 지정된 정수 값을 찾을 때,
// 첫 번째 찾은 값 이전의 항목들 중에서 짝수의 개수를 알아내라!
// 배열에 해당 값이 없으면 -1을 리턴한다. 
//
// 형식:
//   evenNumbersBeforeFixed(양의정수배열, 찾아야할값)
// 예) 
//   evenNumbersBeforeFixed(new int[] {1, 4, 2, 6, 3, 2}, 6) ==> 2
// 
//
/*
Given array of integers sequence and some integer fixedElement, 
output the number of even values in sequence 
before the first occurence of fixedElement or 
-1 if fixedElement is not contained in sequence.

Example

for sequence=[1, 4, 2, 6, 3, 1], fixedElement=6 output should be 2

[input] array.integer sequence
non-empty array of positive integers

[input] integer fixedElement

[output] integer
 */
//
// [시간 복잡도]
// - ?
//
public class Test27 {

  public static void main(String[] args) {
    System.out.println(evenNumbersBeforeFixed(new int[] {1, 4, 2, 6, 3, 2}, 6) == 2);
    System.out.println(evenNumbersBeforeFixed(new int[] {1, 4, 2, 6, 3, 2}, 7) == -1);
  }

  static int evenNumbersBeforeFixed(int[] sequence, int fixedElement) {
    // 이 메서드를 완성하시오!
    return -1;
  }
}







