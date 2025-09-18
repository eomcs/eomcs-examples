// Executors 태스크 프레임워크 - 스레드풀 종료 : shutdownNow()
package com.eomcs.concurrent.ex7;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exam0420 {

  static class MyRunnable implements Runnable {
    String name;

    public MyRunnable(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      try {
        System.out.printf("[%s] '%s' 실행 중...\n", Thread.currentThread().getName(), this.name);

        // 스레드의 실행 시간을 임의로 지연시키기 위함.
        for (long i = 0; i < 1000_0000; i++) {
          double r = Math.tan(3456.77889) / Math.random();
        }

        System.out.printf("[%s] 스레드 종료!\n", Thread.currentThread().getName());

      } catch (Exception e) {
        System.out.printf("[%s] 스레드 실행 중 오류 발생!\n", Thread.currentThread().getName());
      }
    }
  }
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    executorService.execute(new MyRunnable("작업1"));
    executorService.execute(new MyRunnable("작업2"));
    executorService.execute(new MyRunnable("작업3"));
    executorService.execute(new MyRunnable("작업4"));
    executorService.execute(new MyRunnable("작업5"));
    executorService.execute(new MyRunnable("작업6"));

    // 가능한 현재 수행 중인 작업들을 모두 멈추도록 지시한다.
    // => shutdown()과 차이점:
    //    - 만약 Running 상태의 스레드가 Not Runnable 상태(sleep()/wait())에 놓인다면,
    //      바로 스레드를 멈출 기회라고 보고 스레드를 강제 종료할 것이다.
    //    - 즉 실행 중인 작업만 완료시키고, 대기 중인 작업은 취소시키는 효과가 있다.
    // => Running 상태에서 Not Runnable 상태가 될 때까지 기다린다.
    // => 그리고 취소한 대기 작업 목록을 리턴해준다.
    // => "현재 스레드가 실행 중인 작업은 완료하고 나머지 작업은 취소해!."
    //    "취소한 작업 목록은 내가 리턴해줘!"
    List<Runnable> tasks = executorService.shutdownNow();
    System.out.println("실행 취소된 작업들:");
    System.out.println("--------------------------------");
    for (Runnable task : tasks) {
      System.out.println(((MyRunnable) task).name);
    }
    System.out.println("--------------------------------");

    // 물론 새 작업 요청도 거절한다.
    // => 예외 발생!
    //    executorService.execute(new MyRunnable(4000));

    // shutdown() vs shutdownNow();
    // - shutdown()
    //   진행 중인 작업을 완료하고 대기 중인 작업도 완료한 다음 종료.
    // - shutdownNow()
    //   진행 중인 작업은 완료하고, 대기 중인 작업은 취소하고 그 목록을 리턴한다.
    System.out.println("main() 종료!");
  }

}

