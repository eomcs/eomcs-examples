package com.eomcs.quickstart.concurrency.exam04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// ReentrantLock 고급 기능:
//
// synchronized에는 없고 ReentrantLock에만 있는 세 가지 락 획득 방식:
//
//   1. tryLock()
//      - 락을 즉시 획득 시도. 실패하면 대기 없이 즉시 false를 반환한다.
//      - 락이 점유 중일 때 다른 작업을 수행하거나 건너뛸 때 사용한다.
//
//   2. tryLock(long time, TimeUnit unit)
//      - 지정 시간 동안 락 획득 시도. 시간 내 획득하면 true, 초과하면 false.
//      - 무한 대기 없이 타임아웃을 설정할 때 사용한다.
//      - 대기 중 인터럽트가 발생하면 InterruptedException을 던진다.
//
//   3. lockInterruptibly()
//      - 락 대기 중 인터럽트 수신 시 InterruptedException을 던지고 대기를 중단한다.
//      - synchronized는 락 대기 중 인터럽트를 무시하고 계속 대기한다.
//      - 장시간 대기를 취소하거나 타임아웃 없이 인터럽트로 제어할 때 사용한다.
//

public class App2 {

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // 데모 1. tryLock() - 비차단 락 획득 시도
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 1] tryLock() - 락 점유 중이면 즉시 false 반환 (대기 없음)");

    Lock lock1 = new ReentrantLock();

    // 락을 2초간 점유하는 스레드
    Thread holder1 =
        new Thread(
            () -> {
              lock1.lock();
              try {
                System.out.println("  Holder: 락 획득, 2초간 점유");
                Thread.sleep(2000);
                System.out.println("  Holder: 락 해제");
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              } finally {
                lock1.unlock();
              }
            },
            "Holder");

    holder1.start();
    Thread.sleep(200); // Holder가 락을 잡을 시간

    // tryLock() - 락이 점유 중이므로 즉시 false 반환
    boolean acquired = lock1.tryLock();
    if (acquired) {
      try {
        System.out.println("  Main: 락 획득 성공");
      } finally {
        lock1.unlock();
      }
    } else {
      System.out.println("  Main: tryLock() 실패 → 대기 없이 즉시 반환 (다른 작업 수행 가능)");
    }

    holder1.join();

    // Holder 종료 후 tryLock() - 락이 비어 있으므로 즉시 true 반환
    acquired = lock1.tryLock();
    if (acquired) {
      try {
        System.out.println("  Main: Holder 종료 후 tryLock() 성공 ✓");
      } finally {
        lock1.unlock();
      }
    }

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 데모 2. tryLock(timeout, unit) - 타임아웃 있는 락 획득 시도
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 2] tryLock(timeout, unit) - 지정 시간 동안 락 획득 시도");

    Lock lock2 = new ReentrantLock();

    // 락을 1초간 점유하는 스레드
    Thread holder2 =
        new Thread(
            () -> {
              lock2.lock();
              try {
                System.out.println("  Holder2: 락 획득, 1초간 점유");
                Thread.sleep(1000);
                System.out.println("  Holder2: 락 해제");
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              } finally {
                lock2.unlock();
              }
            },
            "Holder2");

    holder2.start();
    Thread.sleep(100); // Holder2가 락을 잡을 시간

    // tryLock(2초): Holder2가 1초 후 해제 → 2초 이내 획득 성공
    Thread waiter =
        new Thread(
            () -> {
              System.out.println("  Waiter: tryLock(2초) 시작 → Holder2 해제까지 최대 2초 대기");
              try {
                boolean got = lock2.tryLock(2, TimeUnit.SECONDS);
                if (got) {
                  try {
                    System.out.println("  Waiter: 락 획득 성공 ✓ (Holder2 해제 후 획득)");
                  } finally {
                    lock2.unlock();
                  }
                } else {
                  System.out.println("  Waiter: 타임아웃 → 락 획득 실패");
                }
              } catch (InterruptedException e) {
                System.out.println("  Waiter: 대기 중 인터럽트");
                Thread.currentThread().interrupt();
              }
            },
            "Waiter");

    waiter.start();
    waiter.join();
    holder2.join();

    System.out.println();

    // tryLock(0.3초): Holder3이 1초 점유 → 0.3초 이내 획득 실패
    Lock lock3 = new ReentrantLock();

    Thread holder3 =
        new Thread(
            () -> {
              lock3.lock();
              try {
                System.out.println("  Holder3: 락 획득, 1초간 점유");
                Thread.sleep(1000);
                System.out.println("  Holder3: 락 해제");
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              } finally {
                lock3.unlock();
              }
            },
            "Holder3");

    holder3.start();
    Thread.sleep(100);

    Thread shortWaiter =
        new Thread(
            () -> {
              System.out.println("  ShortWaiter: tryLock(300ms) 시작 → 타임아웃 예상");
              try {
                boolean got = lock3.tryLock(300, TimeUnit.MILLISECONDS);
                if (got) {
                  try {
                    System.out.println("  ShortWaiter: 락 획득 성공");
                  } finally {
                    lock3.unlock();
                  }
                } else {
                  System.out.println("  ShortWaiter: 300ms 초과 → 락 획득 실패 (타임아웃) ✓");
                }
              } catch (InterruptedException e) {
                System.out.println("  ShortWaiter: 대기 중 인터럽트");
                Thread.currentThread().interrupt();
              }
            },
            "ShortWaiter");

    shortWaiter.start();
    shortWaiter.join();
    holder3.join();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 데모 3. lockInterruptibly() - 락 대기 중 인터럽트 가능
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 3] lockInterruptibly() - 락 대기 중 인터럽트로 취소 가능");
    System.out.println("  synchronized: 락 대기 중 인터럽트를 무시하고 계속 대기한다");
    System.out.println("  lockInterruptibly(): 대기 중 인터럽트 시 InterruptedException 발생");

    Lock lock4 = new ReentrantLock();

    Thread longHolder =
        new Thread(
            () -> {
              lock4.lock();
              try {
                System.out.println("  LongHolder: 락 획득, 5초간 점유");
                Thread.sleep(5000);
              } catch (InterruptedException e) {
                System.out.println("  LongHolder: 인터럽트로 종료");
                Thread.currentThread().interrupt();
              } finally {
                lock4.unlock();
              }
            },
            "LongHolder");

    longHolder.start();
    Thread.sleep(100); // LongHolder가 락을 잡을 시간

    Thread interruptible =
        new Thread(
            () -> {
              System.out.println("  Interruptible: lockInterruptibly() 대기 시작");
              try {
                lock4.lockInterruptibly(); // 락 대기 중 인터럽트 수신 가능
                try {
                  System.out.println("  Interruptible: 락 획득 (예상치 못한 케이스)");
                } finally {
                  lock4.unlock();
                }
              } catch (InterruptedException e) {
                System.out.println("  Interruptible: 락 대기 중 인터럽트 수신 → 작업 취소 ✓");
              }
            },
            "Interruptible");

    interruptible.start();
    Thread.sleep(500); // Interruptible이 대기 상태에 진입할 시간

    System.out.println("  Main: Interruptible 스레드에 인터럽트 전송");
    interruptible.interrupt(); // 락 대기 중인 스레드를 깨움
    interruptible.join();

    longHolder.interrupt();
    longHolder.join();
  }
}
