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

// [주제] 실행자 프레임워크 - 기본 사용법

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test1 {
  public static void main(String[] args) throws Exception {
    System.out.println("단일 스레드 작업 큐(실행자) 생성");
    ExecutorService exec = Executors.newSingleThreadExecutor();
    // - 스레드 한 개를 가진 작업 큐(실행자)를 생성한다.

    System.out.println("작업 큐(실행자)에 작업(task) 수행을 요청");
    exec.execute(
        () -> {
          System.out.println("작업 시작!");
          try {
            Thread.sleep(3000); // 스레드가 3초 동안 작업하는 것을 흉내 낸다.
          } catch (InterruptedException e) {
          }
          System.out.println("작업 끝!");
        });
    // - 작업 큐(실행자)에 작업(task)을 넘기고 실행을 요청한다.

    System.out.println("main 스레드 종료!");
    // - main 스레드가 종료되더라도 non-daemon 스레드가 종료될 때까지 JVM은 종료되지 않는다.

    // [작업이 끝났음에도 JVM이 종료되지 않는 이유]
    // - 스레드가 작업을 마쳤더라도 작업 큐의 스레드는 대기 중이기 때문에 JVM은 종료되지 않는다.

    // [daemon 스레드 vs non-daemon 스레드]
    // daemon 스레드:
    //   - JVM 종료를 막지 않는 스레드
    //   - main 스레드가 종료되면 데몬 스레드도 함께 종료되어 JVM이 바로 종료된다.
    //   - setDaemon(true)로 설정한 스레드
    //   - 예) GC, 모니터링, Timer 등
    // non-daemon 스레드:
    //   - JVM 종료를 막는 스레드
    //   - setDaemon(true)로 설정하지 않은 스레드
    //   - 예) main 스레드, 작업 스레드, 실행자가 만든 스레드 등
    //
  }
}
