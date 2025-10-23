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

// [주제] 실행자 프레임워크 - Executors.newSingleThreadExecutor() 사용법

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test5 {
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
        }
        System.out.printf("[%s] - 작업 종료 후 스레드 대기!\n", Thread.currentThread().getName());
      }
    }

    // 한 개의 스레드만 갖는 작업 큐이다.
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    // 작업 큐에 등록한 작업을 한 번에 한 개씩 순차적으로 처리한다.
    executorService.execute(new MyTask(6000));
    executorService.execute(new MyTask(3000));
    executorService.execute(new MyTask(9000));
    executorService.execute(new MyTask(2000));
    executorService.execute(new MyTask(4000));

    executorService.shutdown();

    System.out.println("main() 종료!");
  }
}
