// 1) 스레드 사용 전
// 2) 스레드 사용 후 : Runnable 구현체를 패키지 멤버로 만들어 Thread로 실행한다.
// 3) 패키지 멤버를 스태틱 중첩 클래스로 만든다.
package com.eomcs.concurrent;

public class Exam0230 {

  public static void main(String[] args) {

    int count = 1000;

    new Thread(new MyRunnable(count)).start();

    for (int i = 0; i < count; i++) {
      System.out.println(">>> " + i);
    }
  }

  static class MyRunnable implements Runnable {

    int count;

    public MyRunnable(int count) {
      this.count = count;
    }

    @Override
    public void run() {
      for (int i = 0; i < count; i++) {
        System.out.println("==> " + i);
      }
    }
  }





}
