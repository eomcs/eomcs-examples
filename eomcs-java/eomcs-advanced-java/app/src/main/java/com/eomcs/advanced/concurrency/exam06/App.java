package com.eomcs.advanced.concurrency.exam06;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// ExecutorService 기본 사용:
//
// Thread를 직접 생성하면 작업마다 새 스레드가 만들어진다.
// ExecutorService는 스레드 풀(Thread Pool)을 만들어 두고, 작업(Runnable)을 큐에 넣어
// 풀 안의 스레드가 하나씩 꺼내 실행하게 한다.
//
// 핵심 개념:
//   Runnable task = () -> { ... };
//   executor.execute(task);  // 결과 없는 작업 제출
//   executor.submit(task);   // Future를 돌려주는 작업 제출
//
// 종료 절차:
//   shutdown()      : 새 작업은 받지 않고, 이미 제출된 작업을 끝까지 실행
//   awaitTermination(timeout, unit): 종료될 때까지 지정 시간 대기
//   shutdownNow()   : 실행 중인 작업에 interrupt를 보내고, 대기 중인 작업을 반환
//
// Thread 직접 생성 vs ExecutorService:
//   new Thread(task).start() : 작업마다 OS 스레드 생성·삭제 → 비용 큼
//   ExecutorService          : 스레드를 풀에 유지·재사용 → 생성 비용 없음
//
// 주요 팩토리 메서드:
//   Executors.newFixedThreadPool(N)    : N개 스레드 고정. 초과 작업은 큐에서 대기
//   Executors.newCachedThreadPool()    : 필요할 때 스레드 생성, 60초 미사용 시 제거
//   Executors.newSingleThreadExecutor(): 스레드 1개, 작업을 순서대로 처리
//   Executors.newScheduledThreadPool(N): 지연·반복 실행 지원 (App4 참고)

public class App {

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // fixed thread pool - 정해진 개수의 스레드로 여러 작업 처리
    // ─────────────────────────────────────────────────────────────
    System.out.println("[기본] Executors.newFixedThreadPool(3)");
    System.out.println("작업 10개를 스레드 3개가 나누어 처리한다.");
    System.out.println();

    // 스레드 3개짜리 고정 크기 풀 생성
    // - 풀 내부에 3개의 작업자 스레드를 미리 만들어 둔다.
    // - 동시에 실행되는 작업 수는 최대 3개이며, 초과 작업은 내부 큐에서 대기한다.
    ExecutorService executor = Executors.newFixedThreadPool(3);

    long start = System.currentTimeMillis();

    for (int i = 1; i <= 10; i++) {
      final int taskNo = i;
      // execute(Runnable): 결과가 필요 없는 작업을 제출한다.
      // - 풀에 남은 스레드가 있으면 즉시 실행, 없으면 내부 큐에서 대기
      // - Runnable은 checked exception을 던질 수 없으므로 try-catch로 처리해야 한다.
      executor.execute(
          () -> {
            String threadName = Thread.currentThread().getName();
            long begin = System.currentTimeMillis() - start;
            System.out.printf("  작업-%02d 시작 +%,4dms (%s)%n", taskNo, begin, threadName);

            try {
              Thread.sleep(500); // 실제 작업 시뮬레이션
            } catch (InterruptedException e) {
              // shutdownNow() 호출 시 인터럽트가 발생한다.
              // interrupt 플래그를 복원해야 호출 스택 상위에서 인터럽트를 감지할 수 있다.
              Thread.currentThread().interrupt();
              System.out.printf("  작업-%02d 인터럽트%n", taskNo);
              return;
            }

            long end = System.currentTimeMillis() - start;
            System.out.printf("  작업-%02d 종료 +%,4dms (%s)%n", taskNo, end, threadName);
          });
    }

    // shutdown(): 새 작업 제출을 막고, 이미 제출된 작업은 끝까지 실행한 뒤 풀을 닫는다.
    // - 이 시점 이후 execute()/submit() 호출 시 RejectedExecutionException 발생
    // - 풀 스레드가 WAITING 상태로 남지 않도록 반드시 호출해야 한다.
    executor.shutdown();

    // awaitTermination(): 풀이 완전히 종료될 때까지 지정 시간(5초)만큼 현재 스레드가 대기한다.
    // - 지정 시간 안에 모든 작업이 끝나면 true 반환
    // - 시간이 초과하면 false 반환 (작업이 아직 실행 중일 수 있음)
    boolean finished = executor.awaitTermination(5, TimeUnit.SECONDS);
    if (!finished) {
      // shutdownNow(): 실행 중인 작업에 interrupt를 보내고, 대기 중인 작업 목록을 반환한다.
      // - 인터럽트를 받은 작업이 즉시 멈추는 것은 보장되지 않는다.
      executor.shutdownNow();
      System.out.println("  제한 시간 초과: 남은 작업 중단 요청");
    }

    long total = System.currentTimeMillis() - start;
    System.out.printf("%n  총 소요 시간: %,dms%n", total);
    System.out.println("→ 스레드는 3개뿐이므로 작업이 3개씩 묶여 실행된다.");
  }
}
