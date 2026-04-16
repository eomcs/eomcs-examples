package com.eomcs.quickstart.concurrency.exam10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

// 가상 스레드 사용 시 주의점:
//
// 가상 스레드는 많이 만들 수 있지만, 외부 자원은 무한하지 않다.
// 예를 들어 DB 커넥션이 10개뿐이라면, 가상 스레드 1,000개가 동시에 DB를 호출해도
// 실제로 동시에 처리할 수 있는 DB 작업은 커넥션 수만큼이다.
//
// 따라서 외부 자원 접근은 Semaphore, 커넥션 풀, rate limiter 등으로 제한해야 한다.
//
// [주의 1] synchronized + 블로킹 = Pinning (고정) 문제:
//   - synchronized 블록 안에서 I/O 블로킹이 발생하면 가상 스레드가 캐리어 스레드에 고정된다.
//   - 가상 스레드가 unmount되지 않아 캐리어 스레드를 독점한다.
//   - 해결책: synchronized 대신 ReentrantLock을 사용한다.
//   - Java 24+에서는 synchronized 블록 내 블로킹도 unmount가 가능하도록 개선됐다.
//
// [주의 2] ThreadLocal 남용:
//   - 가상 스레드를 수백만 개 만들면 ThreadLocal 객체도 그만큼 생긴다.
//   - Java 21+에서는 ScopedValue 사용을 권장한다.
//
// [주의 3] 가상 스레드 풀링(pooling) 금지:
//   - 가상 스레드는 생성 비용이 낮으므로 풀에 넣어 재사용하지 않는다.
//   - newFixedThreadPool()처럼 가상 스레드를 풀링하면 오히려 비효율적이다.

public class App4 {

  public static void main(String[] args) {

    System.out.println("[주의] 가상 스레드와 외부 자원 제한");
    System.out.println("작업은 20개지만, DB 커넥션은 3개뿐이라고 가정한다.");
    System.out.println();

    // Semaphore(3): DB 커넥션을 최대 3개로 제한한다.
    // - 가상 스레드 20개가 동시에 DB를 요청해도 실제 동시 사용은 3개로 제한된다.
    // - acquire()에서 블로킹될 때 가상 스레드가 캐리어 스레드를 해제하므로 효율적이다.
    // - exam05/App4의 커넥션 풀 패턴과 동일한 개념 (App4 참고)
    Semaphore dbConnections = new Semaphore(3);

    // 20개 작업을 가상 스레드로 동시에 시작한다.
    // - try 블록 종료 시 close()가 호출되어 20개 작업 모두 완료될 때까지 대기한다.
    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 1; i <= 20; i++) {
        final int taskNo = i;
        executor.submit(() -> queryDatabase(taskNo, dbConnections));
      }
    } // close(): 20개 가상 스레드 모두 완료 후 executor 종료

    System.out.println();
    System.out.println("→ 가상 스레드는 대기를 싸게 만들지만, 제한된 외부 자원은 별도로 보호해야 한다.");
  }

  private static void queryDatabase(int taskNo, Semaphore dbConnections) {
    try {
      // acquire(): 커넥션이 없으면 대기한다.
      // - 가상 스레드가 블로킹되면 캐리어 스레드는 자동으로 해제되어 다른 가상 스레드가 사용한다.
      // - 따라서 20개 가상 스레드가 대기 중이어도 캐리어 스레드 자원을 낭비하지 않는다.
      dbConnections.acquire();
      try {
        System.out.printf(
            "  작업-%02d DB 사용 시작 (남은 커넥션: %d)%n",
            taskNo, dbConnections.availablePermits());
        Thread.sleep(300); // DB 쿼리 시뮬레이션 (이 블로킹에서도 캐리어 스레드가 해제된다)
        System.out.printf("  작업-%02d DB 사용 종료%n", taskNo);
      } finally {
        // release()는 반드시 finally에서 호출해야 예외가 발생해도 커넥션이 반납된다.
        dbConnections.release();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
