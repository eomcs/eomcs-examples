package com.eomcs.lang.ex06;

//# 흐름 제어문 - break와 continue 활용
//
public class Exam0320 {
  public static void main(String[] args) {
    int count = 0;
    int sum = 0;

    // 1부터 100까지의 합은?
    while (count < 100) {
      //count++;
      //sum += count; // sum = sum + count;
      sum += ++count;
    }
    System.out.printf("count=%d, sum=%d\n", count, sum);
  }
}
