// Executors 태스크 프레임워크 - 스레드풀 종료 대기 : awaitTermination() 활용
package com.eomcs.concurrent.ex7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Exam0520 {

  static class MyRunnable implements Runnable {
    int millisec;

    public MyRunnable(int millisec) {
      this.millisec = millisec;
    }

    @Override
    public void run() {
      try {
        System.out.printf("%s 스레드 실행 중...\n", Thread.currentThread().getName());

        Thread.sleep(millisec);

        System.out.printf("%s 스레드 종료!\n", Thread.currentThread().getName());
      } catch (Exception e) {
        System.out.printf("%s 스레드 실행 중 오류 발생!\n", Thread.currentThread().getName());
      }
    }
  }

  public static void main(String[] args) throws Exception {
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    executorService.execute(new MyRunnable(6000));
    executorService.execute(new MyRunnable(3000));
    executorService.execute(new MyRunnable(9000));
    executorService.execute(new MyRunnable(2000));
    executorService.execute(new MyRunnable(4000));

    // 실행 중인 작업 및 대기 중인 작업이 모두 끝나면 스레드풀을 종료하라!
    executorService.shutdown();

    // 5초를 기다린 후 강제 종료를 시도한다.
    // - 대기 중인 작업은 즉시 취소한다.
    // - 실행 중인 작업은 Thread.interrupt() 를 보낸다.
    if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {

      System.out.println("아직 종료 안된 작업이 있다.");
      System.out.println("종료 시도를 수행!");
      System.out.println("나머지 대기 중인 작업은 강제 종료!");

      executorService.shutdownNow();
    }

    System.out.println("main() 종료!");
  }
}
