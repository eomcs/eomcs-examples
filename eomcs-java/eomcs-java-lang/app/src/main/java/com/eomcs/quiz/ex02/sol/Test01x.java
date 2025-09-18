package com.eomcs.quiz.ex02.sol;

// 출처: codefights.com
//
// 숫자 배열에서 각 이웃하는 숫자간의 차가 가장 큰 것을 알아내기
// 예)
// [2, 4, 1, 0] => 3
// 
// [시간 복잡도]
// - O(n): n은 배열의 개수
public class Test01x {

  public static void main(String[] args) {
    System.out.println(maxDiff(new int[]{2, 4, 1, 0}) == 3);
    System.out.println(maxDiff(new int[]{3, 1, 4, 3, 8, 7}) == 5);
  }

  static int maxDiff(int[] values) {
    int answer = 1;

    // 배열에서 두 개의 수를 꺼내 차를 구한다.
    // 이때 배열은 마지막 전 항목까지 반복한다.
    for (int i = 1; i < values.length; i++) {

      // 두 수의 차는 양수여야 하기 때문에 절대값으로 변환한다.
      int diff = Math.abs(values[i] - values[i - 1]);
      if (diff > answer) {
        answer = diff;
      }

    }
    return answer;
  }
}






