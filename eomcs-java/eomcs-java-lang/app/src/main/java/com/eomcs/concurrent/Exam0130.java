// 1) 스레드 사용 전
// 2) 스레드 사용 후 => 패키지 멤버 클래스로 스레드 구현하기
// 3) 인스턴스 생성 후 즉시 메서드 호출하기
package com.eomcs.concurrent;

public class Exam0130 {

  public static void main(String[] args) {

    int count = 1000;

    new MyThread(count).start();

    for (int i = 0; i < count; i++) {
      System.out.println(">>> " + i);
    }
  }
}