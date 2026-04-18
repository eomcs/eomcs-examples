package com.eomcs.advanced.concurrency.exam05;

import java.util.concurrent.Semaphore;

// Semaphore - 카운팅 세마포어:
//
// 동시에 실행할 수 있는 스레드의 수를 N개로 제한한다.
// permits(허가 수)를 N으로 설정하면 최대 N개의 스레드가 동시에 임계 구역에 진입할 수 있다.
//
// 핵심 메서드:
//   acquire()          : permits를 1 감소. 0이면 양수가 될 때까지 블로킹 대기
//   release()          : permits를 1 증가. 대기 중인 스레드가 있으면 하나를 깨운다
//   availablePermits() : 현재 사용 가능한 permits 수 반환
//
// 동작 원리:
//   acquire() → permits > 0: 즉시 1 감소하고 진입
//            → permits = 0: 양수가 될 때까지 WAITING 상태로 대기
//   release() → permits 1 증가, 대기 중인 스레드 하나를 깨운다
//
// synchronized / Lock vs Semaphore:
//   synchronized / Lock : 한 번에 한 스레드만 (= Semaphore(1), 뮤텍스)
//   Semaphore(N)        : 한 번에 최대 N개 스레드 동시 허용 (카운팅 세마포어)
//
// 사용 패턴:
//   semaphore.acquire();
//   try {
//     // 임계 구역
//   } finally {
//     semaphore.release(); // 예외 발생 시에도 반드시 반납
//   }
//

public class App {

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // 동시 접근 수 제한 - Semaphore(3): 최대 3개 스레드 동시 허용
    // ─────────────────────────────────────────────────────────────
    System.out.println("[기본] Semaphore(3) - 동시에 최대 3개 스레드 허용");
    System.out.println("10개 스레드 × 각 500ms 작업 → 3개씩 그룹으로 실행");
    System.out.println("기대 총 시간: 500ms × ⌈10/3⌉ = 500ms × 4 ≈ 2,000ms");
    System.out.println();

    Semaphore semaphore = new Semaphore(3); // 동시 접근 허용 수: 3
    long start = System.currentTimeMillis();
    Thread[] threads = new Thread[10];

    for (int i = 0; i < 10; i++) {
      final int id = i;
      threads[i] =
          new Thread(
              () -> {
                try {
                  semaphore.acquire(); // permits 1 감소. permits=0이면 대기
                  try {
                    long t = System.currentTimeMillis() - start;
                    System.out.printf("  Thread-%2d: 진입 +%,4dms%n", id, t);
                    Thread.sleep(1000); // 임계 구역 실행 시뮬레이션
                    long elapsed = System.currentTimeMillis() - start;
                    System.out.printf("  Thread-%2d: 종료 +%,4dms%n", id, elapsed);
                  } finally {
                    semaphore.release(); // permits 1 증가 → 대기 중인 스레드 깨움
                  }
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                }
              },
              "T-" + id);
    }

    for (Thread t : threads) t.start();
    for (Thread t : threads) t.join();

    long totalTime = System.currentTimeMillis() - start;
    System.out.printf("%n  총 소요 시간: %,dms%n", totalTime);
    System.out.println("→ 3개씩 그룹으로 실행됨. Semaphore가 동시 진입 수를 제한함.");
  }
}
