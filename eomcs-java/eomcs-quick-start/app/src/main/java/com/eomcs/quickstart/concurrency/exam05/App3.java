package com.eomcs.quickstart.concurrency.exam05;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// Semaphore 고급 기능:
//
// 1. tryAcquire()
//    - permits 획득 시도. 즉시 성공(true) 또는 실패(false) 반환. 대기 없음.
//    - ReentrantLock.tryLock()과 동일한 개념
//
// 2. tryAcquire(long timeout, TimeUnit unit)
//    - 지정 시간 동안 permits 획득 시도. 시간 내 획득하면 true, 초과하면 false.
//    - 대기 중 인터럽트가 발생하면 InterruptedException을 던진다.
//
// 3. acquire(int permits) / release(int permits)
//    - 한 번에 N개의 permits를 획득하거나 반납한다.
//    - 대용량 작업이 소용량 작업보다 더 많은 자원을 점유할 때 사용한다.
//
// 4. new Semaphore(N, true) - 공정 세마포어(Fair Semaphore)
//    - 기본 세마포어(fair=false): 대기 순서 무관하게 임의의 스레드가 허가를 얻는다
//    - 공정 세마포어(fair=true): 먼저 대기한 스레드가 먼저 허가를 얻는다 (FIFO)
//    - 공정성 보장에는 약간의 성능 오버헤드가 있다
//

public class App3 {

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // 데모 1. tryAcquire() - 비차단 획득 시도
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 1] tryAcquire() - 즉시 시도, 실패 시 대기 없이 반환");

    Semaphore sem1 = new Semaphore(2); // 동시 2개 허용

    // 2개 스레드가 permits를 모두 점유
    Thread holder1 =
        new Thread(
            () -> {
              try {
                sem1.acquire();
                System.out.println("  Holder-1: 허가 획득, 2초간 점유");
                Thread.sleep(2000);
                System.out.println("  Holder-1: 허가 반납");
                sem1.release();
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "Holder-1");

    Thread holder2 =
        new Thread(
            () -> {
              try {
                sem1.acquire();
                System.out.println("  Holder-2: 허가 획득, 2초간 점유");
                Thread.sleep(2000);
                System.out.println("  Holder-2: 허가 반납");
                sem1.release();
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "Holder-2");

    holder1.start();
    holder2.start();
    Thread.sleep(200); // Holder들이 허가를 잡을 시간

    // tryAcquire() - permits=0이므로 즉시 false 반환
    boolean got = sem1.tryAcquire();
    System.out.printf("  Main tryAcquire(): %s (사용 가능: %d)%n",
        got ? "성공" : "실패 → 대기 없이 즉시 반환", sem1.availablePermits());

    holder1.join();
    holder2.join();

    // Holder들이 반납 후 tryAcquire() - 성공
    got = sem1.tryAcquire();
    System.out.printf("  Main tryAcquire() (반납 후): %s ✓%n", got ? "성공" : "실패");
    if (got) sem1.release();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 데모 2. tryAcquire(timeout, unit) - 타임아웃 있는 획득 시도
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 2] tryAcquire(timeout, unit) - 지정 시간 동안 획득 시도");

    Semaphore sem2 = new Semaphore(1);

    Thread longHolder =
        new Thread(
            () -> {
              try {
                sem2.acquire();
                System.out.println("  LongHolder: 허가 획득, 1초간 점유");
                Thread.sleep(1000);
                System.out.println("  LongHolder: 허가 반납");
                sem2.release();
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "LongHolder");

    longHolder.start();
    Thread.sleep(100);

    // 2초 타임아웃 → LongHolder가 1초 후 반납 → 성공
    Thread waiter =
        new Thread(
            () -> {
              System.out.println("  Waiter: tryAcquire(2초) 시작");
              try {
                boolean acquired = sem2.tryAcquire(2, TimeUnit.SECONDS);
                System.out.printf("  Waiter: %s%n",
                    acquired ? "성공 ✓ (LongHolder 반납 후 획득)" : "타임아웃 → 실패");
                if (acquired) sem2.release();
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "Waiter");

    waiter.start();
    waiter.join();
    longHolder.join();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 데모 3. acquire(N) / release(N) - N개 permits 동시 획득·반납
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 3] acquire(N) / release(N) - N개 permits 동시 획득·반납");
    System.out.println("총 permits=10, 대용량 작업(3개) + 소용량 작업(1개) 혼용");

    Semaphore sem3 = new Semaphore(10); // 총 자원 10 단위

    Thread heavyTask =
        new Thread(
            () -> {
              try {
                sem3.acquire(3); // 3개 한 번에 획득 (대용량 작업)
                try {
                  System.out.printf("  HeavyTask: 3개 획득 (남은: %d)%n",
                      sem3.availablePermits());
                  Thread.sleep(800);
                } finally {
                  sem3.release(3); // 3개 한 번에 반납
                  System.out.printf("  HeavyTask: 3개 반납 (남은: %d)%n",
                      sem3.availablePermits());
                }
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "HeavyTask");

    Thread lightTask =
        new Thread(
            () -> {
              try {
                sem3.acquire(1); // 1개만 획득 (소용량 작업)
                try {
                  System.out.printf("  LightTask: 1개 획득 (남은: %d)%n",
                      sem3.availablePermits());
                  Thread.sleep(400);
                } finally {
                  sem3.release(1);
                  System.out.printf("  LightTask: 1개 반납 (남은: %d)%n",
                      sem3.availablePermits());
                }
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "LightTask");

    heavyTask.start();
    lightTask.start();
    heavyTask.join();
    lightTask.join();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 데모 4. 공정 세마포어(Fair Semaphore)
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 4] 공정 세마포어 - new Semaphore(1, true)");
    System.out.println("fair=false (기본): 대기 순서 무관, 임의의 스레드 획득");
    System.out.println("fair=true        : 먼저 대기한 스레드가 먼저 획득 (FIFO)");
    System.out.println();

    Semaphore fairSem = new Semaphore(1, true); // 공정 세마포어

    // 점유 스레드 (3초 점유)
    Thread fairHolder =
        new Thread(
            () -> {
              try {
                fairSem.acquire();
                Thread.sleep(500); // 대기 스레드들이 줄을 설 시간
                System.out.println("  FairHolder: 허가 반납");
                fairSem.release();
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "FairHolder");

    fairHolder.start();
    Thread.sleep(100);

    // 순서대로 대기하는 스레드들
    Thread[] waiters = new Thread[5];
    for (int i = 0; i < 5; i++) {
      final int id = i;
      waiters[i] =
          new Thread(
              () -> {
                try {
                  fairSem.acquire();
                  try {
                    System.out.printf("  대기-%d: 획득 (fair=true → 대기 순서대로 획득)%n", id);
                    Thread.sleep(50);
                  } finally {
                    fairSem.release();
                  }
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                }
              },
              "Waiter-" + i);
      waiters[i].start();
      Thread.sleep(30); // 대기 순서가 명확하도록 순차적으로 시작
    }

    fairHolder.join();
    for (Thread w : waiters) w.join();
    System.out.println("→ fair=true: 대기 순서(0→1→2→3→4)대로 획득 ✓");
  }
}
