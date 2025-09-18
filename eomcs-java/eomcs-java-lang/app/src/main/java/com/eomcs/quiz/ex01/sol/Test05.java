package com.eomcs.quiz.ex01.sol;

// 출처: codefights.com
//
// 한번에 한 자리의 숫자만 바꿀 때 이전 값과 다르게 만들 수 있는 경우는 몇가지인가?
// 단, 맨 앞의 숫자는 0이 될 수 없다. 바꾸는 숫자는 현재의 숫자 보다 커야 한다.
// 
// 예)
// 10 => 17 번 
//
// [시간 복잡도]
// - O(k) : k는 10진수의 자릿수이다.
//
public class Test05 {

  public static void main(String[] args) {
    System.out.println(countWaysToChangeDigit(10) == 17);
    System.out.println(countWaysToChangeDigit(2345) == 22);
  }

  static int countWaysToChangeDigit(int value) {
    int answer = 0;  
    while (value > 0) {    
      answer += 9 - value % 10;
      value /= 10;
    }
    return answer;
  }
}
