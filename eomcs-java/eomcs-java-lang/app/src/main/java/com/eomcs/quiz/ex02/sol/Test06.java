package com.eomcs.quiz.ex02.sol;

// 출처: codefights.com
// 
// 분수의 절대 값이 배열로 주어질 때, 
//   분자: 배열[0]
//   분모: 배열[1]
// 분수가 1보다 작다면 "Proper"를 출력하고 
// 분수가 1이상이면 "Improper"를 출력하라!
//
// 예) properOrImproper([7, 2]) = "Improper"
// 
//   
/*
A fraction written as a/b is said to be a proper fraction 
if the absolute value of the fraction is strictly less than one. 
Otherwise it is called an improper fraction.

Determine whether a given fraction is proper.

Example

properOrImproper([7, 2]) = "Improper"

[input] array.integer A
array of two integers representing a fraction A[0] / A[1]

[output] string
"Proper" if A contains a proper fraction, "Improper" otherwise
 */
//
// [시간 복잡도]
// - O(1) : 시간 복잡도는 일정하다.
//
public class Test06 {
  public static void main(String[] args) {
    System.out.println(properOrImproper(new int[]{7,2}) == "Improper");
    System.out.println(properOrImproper(new int[]{2,7}) == "Proper");
    System.out.println(properOrImproper(new int[]{-3,3}) == "Improper");
  }

  static String properOrImproper(int[] A) {
    if (Math.abs(A[0] / A[1]) < 1) {
      return "Proper";
    }
    return "Improper";
  }


}
