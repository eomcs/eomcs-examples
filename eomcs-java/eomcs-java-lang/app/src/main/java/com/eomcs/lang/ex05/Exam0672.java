package com.eomcs.lang.ex05;

//# 증감 연산자 : 전위(pre-fix) 증감 연산자 응용 I
//
public class Exam0672 {
  public static void main(String[] args) {
    int i = 2;
    i = ++i;
    // 위의 문장은 다음 두 개의 문장으로 바뀐다.
    // i = i + 1;
    // i = i;

    System.out.println(i); // 3
  }
}
