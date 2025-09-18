package com.eomcs.quiz.ex01.sol;

// 출처: codefights.com
//
// 주어진 숫자에 짝수가 몇 개인지 세어라.
// 예) 
// 1010 => 2
// 123 => 1
//
// [시간 복잡도]
// - O(k) : k는 입력 값의 10진수 자릿수이다.
//
public class Test04 {

  public static void main(String[] args) {
    System.out.println(countEvenNumber(1238694636) == 6);
    System.out.println(countEvenNumber(2567884) == 5);
  }

  static int countEvenNumber(int value) {
    int result = 0;
    int n = value;
    while(n != 0) {
      if ((n % 2) == 0) {
        result++;
      }
      n /= 10;
    }
    return result;
  }
}
