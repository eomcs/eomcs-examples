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

// [주제] 실행자 프레임워크 - showdown() 사용법

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test2 {
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

    System.out.println("작업 큐 종료 요청");
    exec.shutdown();
    // - 작업 큐의 스레드들이 작업을 마치면 대기하지 말고 즉시 종료하도록 요청한다.
    // - 요청 후 즉시 리턴한다.

    System.out.println("main 스레드 종료!");
    // - main 스레드가 종료되더라도 non-daemon 스레드가 종료될 때까지 JVM은 종료되지 않는다.
    // - 작업 큐의 모든 스레드들이 작업을 마치고, 대기 중인 스레드가 없으면 JVM은 종료된다.
  }
}
