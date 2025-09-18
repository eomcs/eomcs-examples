// Executors 태스크 프레임워크 - 스레드풀 종료 대기 : awaitTermination()
package com.eomcs.concurrent.ex7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Exam0510 {

  static class MyRunnable implements Runnable {
    int millisec;

    public MyRunnable(int millisec) {
      this.millisec = millisec;
    }

    @Override
    public void run() {
      try {
        System.out.printf("%s 스레드 실행 중...\n",
            Thread.currentThread().getName());

        Thread.sleep(millisec);

        System.out.printf("%s 스레드 종료!\n",
            Thread.currentThread().getName());
      } catch (Exception e) {
        System.out.printf("%s 스레드 실행 중 오류 발생!\n", Thread.currentThread().getName());
      }
    }
  }
  public static void main(String[] args) throws Exception {
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    executorService.execute(new MyRunnable(6000));
    executorService.execute(new MyRunnable(2000));
    executorService.execute(new MyRunnable(4000));
    executorService.execute(new MyRunnable(15000));

    executorService.shutdown();

    // 스레드풀의 모든 스레드가 종료되면 즉시 true를 리턴한다.
    // 만약 지정된 시간(예: 10초)이 경과할 때까지 종료되지 않았다면 false를 리턴한다.
    //
    if (!executorService.awaitTermination(20, TimeUnit.SECONDS)) {
      System.out.println("아직 종료 안된 작업이 있다.");
    } else {
      System.out.println("모든 작업을 종료하였다.");
    }

    System.out.println("main() 종료!");
  }
}

