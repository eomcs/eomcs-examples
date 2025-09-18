// Executors 태스크 프레임워크 - 스레드풀 만들기 : 한 개의 스레드를 갖는 스레드풀
package com.eomcs.concurrent.ex7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exam0230 {

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

    // 한 개의 스레드만 갖는 스레드풀이다.
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    // 스레드가 한 개이기 때문에 순차적으로 실행한다.
    executorService.execute(new MyRunnable(6000));
    executorService.execute(new MyRunnable(3000));
    executorService.execute(new MyRunnable(9000));
    executorService.execute(new MyRunnable(2000));
    executorService.execute(new MyRunnable(4000));

    executorService.shutdown();

    System.out.println("main() 종료!");
  }
}

