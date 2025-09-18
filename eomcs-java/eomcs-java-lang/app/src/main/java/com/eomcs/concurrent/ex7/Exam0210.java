// Executors 태스크 프레임워크 - 스레드풀 만들기 : 고정크기 스레드풀
package com.eomcs.concurrent.ex7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exam0210 {

  static class MyRunnable implements Runnable {
    int millisec;

    public MyRunnable(int millisec) {
      this.millisec = millisec;
    }

    @Override
    public void run() {
      try {
        System.out.printf("[%s] - 스레드에서 작업 실행 중...(%d)\n",
            Thread.currentThread().getName(), millisec);

        Thread.sleep(millisec);

        System.out.printf("[%s] - 작업 종료 후 스레드 대기!\n",
            Thread.currentThread().getName());
      } catch (Exception e) {
        System.out.printf("[%s] 스레드 실행 중 오류 발생!\n", Thread.currentThread().getName());
      }
    }
  }
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    // 일단 스레드풀의 크기(3 개)만큼 작업 수행을 요청한다.
    // - 작업은 큐에 등록된 순서대로 보관된다.
    // - 스레드풀은 큐에서 작업을 꺼내 스레드에게 일을 시킨다.
    //
    executorService.execute(new MyRunnable(6000));
    executorService.execute(new MyRunnable(3000));
    executorService.execute(new MyRunnable(9000));

    // 스레드풀의 크기를 초과해서 작업 수행을 요청한다면?
    // - 놀고 있는 스레드가 없을 경우,
    //   다른 스레드의 작업이 끝날 때까지 작업큐에 대기하고 있는다.
    // - 작업을 끝낸 스레드가 생기면 큐에서 작업을 꺼내 실행한다.
    //
    executorService.execute(new MyRunnable(2000));
    executorService.execute(new MyRunnable(4000));

    executorService.shutdown();

    System.out.println("main() 종료!");
  }
}

