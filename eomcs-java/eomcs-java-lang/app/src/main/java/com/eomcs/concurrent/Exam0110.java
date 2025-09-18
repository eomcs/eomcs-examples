// 1) 스레드 사용 전
package com.eomcs.concurrent;

public class Exam0110 {

  public static void main(String[] args) {

    int count = 1000;

    for (int i = 0; i < count; i++) {
      System.out.println("==> " + i);
    }

    for (int i = 0; i < count; i++) {
      System.out.println(">>> " + i);
    }
  }
}
