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

    // 1) 새 작업 거부
    //    - 호출 순간부터 새로운 submit() / execute() 요청은 모두 RejectedExecutionException 으로 거부한다.
    // 2) 현재 실행 중인 스레드에 interrupt() 시도
    //    - 풀 안의 모든 실행 중인 스레드에 Thread.interrupt() 를 보낸다.
    //    - 각 작업은 InterruptedException 을 받거나, 인터럽트 플래그를 보고 중단할 수 있다.
    //    - 단, 작업 코드가 인터럽트를 무시하면 즉시 중단되지 않습니다.
    // 3) 대기 중(아직 시작하지 않은) 작업 제거 및 반환
    //    - 큐에 남아 있던 아직 실행되지 않은 작업들을 꺼내서 List<Runnable> 형태로 반환한다.
    //    - 즉, “대기열에 있던 작업들”은 실행되지 않는다.
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
