package com.eomcs.lang.ex07;

//# 메서드 : 스택 오버플로우 오류!
//
public class Exam0460 {

  static int sum(int value) {
    System.out.println(value);
    if (value == 1)
      return 1;

    return value + sum(value - 1);
  }

  public static void main(String[] args) {

    // 스택 오버플로우(stack overflow)?
    // => JVM 스택 메모리가 꽉 차서 더이상 
    //    메서드 실행에 필요한 로컬 변수를 만들 수 없는 상태이다.
    //
    // 예) 
    // 다음과 같이 큰 수를 계산할 때는 
    // 재귀호출의 수가 많아져서 쉽게 스택 메모리가 부족해진다.
    // 따라서 호출 단계가 깊지 않은 작은 수를 다룰 경우에는 
    // 재귀호출을 써도 되지만,
    // 호출 단계가 많은 큰 수를 다룰 때는 재귀호출 대신 반복문을 사용하라!
    System.out.println(sum(19100));

    // 메소드 호출이 너무 깊어지는 경우는 재귀호출 대신 다른 방법을 사용하라.
    /*
    long sum = 0;
    for (int i = 1; i <= 100000; i++) {
      sum += i;
    }
    System.out.println(sum);
     */
  }
}






