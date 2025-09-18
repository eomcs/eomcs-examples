package com.eomcs.quiz.ex02.sol;

// 출처: codefights.com
//
// 숫자 배열에서 각 이웃하는 숫자간의 차가 가장 큰 것을 알아내기
// 예)
// [2, 4, 1, 0] => 3
// 
// [시간 복잡도]
// - O(n) : n은 배열의 개수이다.
//
public class Test01 {

  public static void main(String[] args) {
    System.out.println(maxDiff(new int[]{2, 4, 1, 0}) == 3);
    System.out.println(maxDiff(new int[]{3, 1, 4, 3, 8, 7}) == 5);
  }

  static int maxDiff(int[] values) {
    int answer = 1;
    for (int i = 1; i < values.length; i++) {
      if (Math.abs(values[i] - values[i - 1]) > answer) {
        answer = Math.abs(values[i] - values[i-1]);
      }
    }

    return answer;
  }
}
