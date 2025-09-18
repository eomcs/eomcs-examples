// 스레드의 생명주기(lifecycle) - 우선 순위 설정
package com.eomcs.concurrent.ex4;

public class Exam0220 {
  public static void main(String[] args) throws Exception {
    class MyThread extends Thread {
      public MyThread(String name) {
        super(name);
      }

      @Override
      public void run() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++)
          Math.asin(38.567); // 시간 끌기 용. 왜? 부동소수점 연산은 시간을 많이 소요.
        long endTime = System.currentTimeMillis();
        System.out.printf("%s 스레드 실행 시간: %d\n", getName(), endTime - startTime);
      }
    }

    MyThread t1 = new MyThread("홍길동(1)");
    t1.setPriority(1);

    MyThread t2 = new MyThread("임꺽정(10)");
    t2.setPriority(10);

    // 유닉스 계열의 OS는 스케줄링에서 우선 순위를 고려하여 CPU를 배분한다.
    // 그러나 Windows OS는 우선 순위를 덜 고려하여 CPU를 배분한다.
    // 그러다보니 우선 순위를 조정하여 작업을 처리하도록 프로그램을 짜게 되면,
    // 유닉스 계열에서 실행할 때는 의도한 대로 동작할지 모르지만,
    // 윈도우에서는 의도대로 동작하지 않을 것이다.
    // 따라서 프로그램을 짤 때 스레드의 우선 순위를 조정하는 방법에 의존하지 말라!

    // 스레드 작업 시작
    t1.start();
    t2.start();

  }

}


