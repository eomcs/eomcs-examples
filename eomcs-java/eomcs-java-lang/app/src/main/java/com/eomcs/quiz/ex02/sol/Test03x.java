package com.eomcs.quiz.ex02.sol;

// 출처: codefights.com
//
// 숫자 배열이 있다.
// 나누어 0이 떨어지는 수가 몇 쌍이 있는지 구하라!
//
// [시간 복잡도]
// - O(n) : n은 배열의 개수이다.
//
public class Test03x {

  public static void main(String[] args) {
    int[] values = {2, 4, 5, 8, 10};
    System.out.println(divisorsPairs(values) == 5);

  }

  public static int divisorsPairs(int[] sequence) {
    int result = 0;

    // 배열은 0 번 항목부터 반복한다.
    for (int i = 0; i < sequence.length; i++) {

      // 나눌 값은 i 번 다음 항목과 나누기 계산한다.
      for (int j = i + 1; j <sequence.length; j++) {
        //System.out.println(sequence[i] + "<--->" + sequence[j]);
        int iValue = sequence[i]; // i 번째 항목의 값을 꺼내고
        int jValue = sequence[j]; // i 번 다음 항목의 값을 꺼낸다.

        // 두 항목을 바꿔가면서 나눗셈을 하여 0으로 떨어지는지 조사한다.
        if ((iValue % jValue == 0) || ((jValue % iValue) == 0)) {
          result++;
        }
      }
    }

    return result;
  }
}