package com.eomcs.lang.ex07;

// # 메서드 : 메서드 중첩 호출
//
public class Exam0280 {

  public static void main(String[] args) {
    // 2 + 3 + 4 + 5 = ?
    //
    // 1) 메서드의 리턴 값을 변수로 받을 때
    int result = plus(2, 3);
    result = plus(result, 4); // result 변수가 넘어가는 것이 아니라 result 변수의 값이 넘어 간다.
    result = plus(result, 5);
    System.out.println(result);

    // 2) 메서드의 리턴 값을 바로 파라미터에 전달할 때
    result = plus(plus(plus(2, 3), 4), 5);
    // 실행 과정
    // - 메서드를 호출하는 문장의 가장 안쪽부터 실행된다.
    //
    // result = plus(plus(5, 4),5);
    // result = plus(9, 5);
    // result = 14;
    //
    System.out.println(result);

    System.out.println(plus(plus(plus(2, 3), 4), 5));

    int r = plus(100, 200);
    System.out.printf("100 + 200 = %d\n", r);

    // 위의 문장은 다음과 같다.
    System.out.printf("100 + 200 = %d\n", plus(100, 200));
  }

  static int plus(int a, int b) {
    return a + b;
  }
}
