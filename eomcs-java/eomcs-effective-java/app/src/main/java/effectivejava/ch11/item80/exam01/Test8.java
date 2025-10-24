// # 아이템 80. 스레드보다는 실행자, 태스크, 스트림을 애용하라
// [실행자 프레임워크]
// - 인터페이스 기반의 유연한 태스크 실행 기능을 담고 있다.
// - 주요 기능:
//   - 특정 태스크가 완료되기를 기다린다.
//   - 태스크 모음 중 아무것 하나(invokeAny 메서드)
//     혹은 모든 태스크(invokeAll 메서드)가 완료되기를 기다린다.
//   - 실행자 서비스가 종료하기를 기다린다(awaitTermination 메서드).
//   - 완료된 태스크들의 결과를 차례로 받는다(ExecutorCompletionService 이용).
//   - 태스크를 특정 시간에 혹은 주기적으로 실행하게 한다(ScheduledThreadPoolExecutor 이용).
//

package effectivejava.ch11.item80.exam01;

// [주제] 실행자 프레임워크 - Executors.shutdownNow() 사용법

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test8 {
  public static void main(String[] args) throws Exception {

    class MyTask implements Runnable {
      final String name;
      final boolean delay;

      public MyTask(String name, boolean delay) {
        this.name = name;
        this.delay = delay;
      }

      @Override
      public void run() {
        System.out.printf("[%s] - 스레드에서 작업 실행 중...\n", Thread.currentThread().getName());

        try {
          if (delay) {
            Thread.sleep(3000);
          }
        } catch (InterruptedException e) {
          System.out.println(name + " 인터럽트 발생!");
        }

        long start = System.currentTimeMillis();
        int count = 0;
        while (true) {
          long end = System.currentTimeMillis();
          if (end - start > 5000) { // 1초 경과
            break;
          }
        }
        System.out.printf("[%s] - 작업 종료!\n", Thread.currentThread().getName());
      }

      public String toString() {
        return name;
      }
    }

    // 최대 3개의 스레드를 갖는 작업 큐를 생성한다.
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    executorService.execute(new MyTask("작업1", false));
    executorService.execute(new MyTask("작업2", true));
    executorService.execute(new MyTask("작업3", false));
    executorService.execute(new MyTask("작업4", false));
    executorService.execute(new MyTask("작업5", false));

    // 3개 스레드가 작업을 시작하도록 잠시 기다린다.
    Thread.sleep(3000);

    List<Runnable> canceledTasks = executorService.shutdownNow();
    // shutdownNow(): Forceful Shutdown
    // 1) 새 작업 제출
    //    - 호출 순간부터 새로운 submit() / execute() 요청은
    //      모두 RejectedExecutionException 으로 거부한다.
    // 2) 큐에 있는 작업
    //    - 실행 대기 중 작업을 취소한다.
    //    - 즉, “대기열에 있던 작업들”은 실행되지 않는다.
    // 3) 실행 중 작업
    //    - 풀 안의 모든 실행 중인 스레드에 Thread.interrupt() 를 보낸다.
    //    - 각 작업은 InterruptedException 을 받거나, 인터럽트 플래그를 보고 중단할 수 있다.
    //    - 단, 작업 코드가 인터럽트를 무시하면 즉시 중단되지 않습니다.
    // 4) 반환 시점
    //    - 즉시 반환
    //    - List<Runnable> 형태로 반환한다.
    // 5) 보장
    //    - "가능한 빨리" 종료 시도
    // 6) 상태 플래스
    //    - isShutdown() : true(즉시)
    //    - isTerminated() : 작업이 모두 끝나야 true

    for (Runnable task : canceledTasks) {
      System.out.println("취소된 작업: " + ((MyTask) task).name);
    }
    System.out.println("main() 종료!");
  }
}
