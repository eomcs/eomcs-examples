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

// [주제] 실행자 프레임워크 - Executors.shutdown() 사용법 II

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test7 {
  public static void main(String[] args) throws Exception {
    class MyTask implements Runnable {
      final long millisec;

      MyTask(long millisec) {
        this.millisec = millisec;
      }

      @Override
      public void run() {
        System.out.printf(
            "[%s] - 스레드에서 작업 실행 중...(%d)\n", Thread.currentThread().getName(), millisec);
        try {
          Thread.sleep(millisec);
        } catch (InterruptedException e) {
          System.out.println("인터럽트 발생!");
        }
        System.out.printf("[%s] - 작업 종료 후 스레드 대기!\n", Thread.currentThread().getName());
      }
    }

    // 최대 3개의 스레드를 갖는 작업 큐를 생성한다.
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    executorService.execute(new MyTask(6000));
    executorService.execute(new MyTask(3000));
    executorService.execute(new MyTask(9000));
    executorService.execute(new MyTask(2000));

    executorService.shutdown();
    // shutdown(): Graceful Shutdown
    // 1) 새 작업 제출
    //    - 호출 순간부터 새로운 submit() / execute() 요청은
    //      모두 RejectedExecutionException 으로 거부한다.
    // 2) 큐에 있는 작업
    //    - 모두 실행 된다.
    //    - 이미 제출된 것은 끝까지 처리한다.
    // 3) 실행 중 작업
    //    - 완료될 때까지 기다린다. 인터럽트를 걸지 않는다.
    // 4) 반환 시점
    //    - 즉시 반환
    // 5) 보장
    //    - "언젠가 정상 종료"로 수렴
    // 6) 상태 플래스
    //    - isShutdown() : true(즉시)
    //    - isTerminated() : 작업이 모두 끝나야 true

    try {
      executorService.execute(new MyTask(4000));
    } catch (Exception e) {
      System.out.println("작업 요청 거절됨!");
    }

    System.out.println("main() 종료!");
  }
}
