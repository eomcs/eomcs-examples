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

// [주제] 실행자 프레임워크 - Executors.submit()/get() 사용법

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test6 {
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

    // 최대 3개의 스레드를 갖는 작업 큐를 생성한다.
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    // submit()
    // - execute() 와 같다.
    // - 단, 작업의 종료 상태를 확인할 수 있는 Future 객체를 리턴한다.
    // - 커피숍에서 주문한 후 알림벨을 받는 것과 같다.
    Future<?> future1 = executorService.submit(new MyTask(2000));
    Future<?> future2 = executorService.submit(new MyTask(4000));

    executorService.shutdown();

    // Future.get()
    // => 요청한 작업이 완료될 때 까지 기다린다.(pending)
    // => 요청한 작업이 완료되면 null을 리턴한다.
    //
    future2.get(); // 두 번째 작업이 끝날 때까지 기다린다.
    System.out.println("두 번째 작업이 끝났음");

    future1.get(); // 첫 번째 작업이 끝날 때까지 기다린다.
    System.out.println("첫 번째 작업이 끝났음");

    System.out.println("main() 종료!");
  }
}
