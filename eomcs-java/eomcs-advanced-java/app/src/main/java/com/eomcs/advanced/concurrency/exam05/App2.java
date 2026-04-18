package com.eomcs.advanced.concurrency.exam05;

import java.util.concurrent.Semaphore;

// Semaphore(1) - 뮤텍스:
//
// permits=1인 세마포어는 한 번에 한 스레드만 허용하므로
// synchronized / Lock과 동일한 상호 배제(mutual exclusion)를 구현한다.
//
// Semaphore(1) vs synchronized / Lock의 핵심 차이 - 소유권(Ownership):
//   synchronized / Lock : 락을 획득한 스레드만 해제할 수 있다 (소유권 있음)
//   Semaphore(1)        : 어떤 스레드든 release()를 호출할 수 있다 (소유권 없음)
//
// 소유권이 없으면 신호(signal) 패턴을 구현할 수 있다:
//
//   Semaphore(0) 신호 패턴:
//   - 초기 permits=0 → acquire()는 즉시 대기 상태로 진입
//   - 다른 스레드가 release() → 대기 중인 스레드를 깨운다
//   - 용도: 스레드 A가 스레드 B의 완료를 기다릴 때
//   - Thread.join()과 유사하지만, 작업 중간 신호 지점을 자유롭게 지정할 수 있다
//

public class App2 {

  // Semaphore(1)로 동기화를 처리한 계좌 클래스
  static class Bank {

    private int balance;
    private final Semaphore semaphore = new Semaphore(1); // permits=1 → 뮤텍스

    public Bank(int initialBalance) {
      this.balance = initialBalance;
    }

    public void deposit(int amount) {
      try {
        semaphore.acquire(); // permits 1 → 0 (다른 스레드는 acquire에서 대기)
        try {
          int current = balance;
          Thread.yield();
          balance = current + amount;
        } finally {
          semaphore.release(); // permits 0 → 1 (대기 중인 스레드 깨움)
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    public void withdraw(int amount) {
      try {
        semaphore.acquire();
        try {
          if (balance >= amount) {
            Thread.yield();
            balance -= amount;
          }
        } finally {
          semaphore.release();
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    public int getBalance() {
      return balance;
    }
  }

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // 파트 1. Semaphore(1) - 뮤텍스로 Bank 동기화
    // ─────────────────────────────────────────────────────────────
    System.out.println("[파트 1] Semaphore(1) - 뮤텍스, Bank 동기화");

    System.out.println("해결 1: 갱신 분실 해결 - 10개 스레드 × 1,000회 입금(1원) → 기대: 10,000원");
    int threadCount = 10;
    int depositCount = 1_000;

    for (int trial = 1; trial <= 3; trial++) {
      Bank bank = new Bank(0);
      Thread[] threads = new Thread[threadCount];
      for (int i = 0; i < threadCount; i++) {
        threads[i] =
            new Thread(
                () -> {
                  for (int j = 0; j < depositCount; j++) bank.deposit(1);
                },
                "입금-" + i);
      }
      for (Thread t : threads) t.start();
      for (Thread t : threads) t.join();

      int finalBalance = bank.getBalance();
      String result = (finalBalance == threadCount * depositCount) ? "정상 ✓" : "오류!";
      System.out.printf("  시도 %d: 최종 잔액 = %,d원 [%s]%n", trial, finalBalance, result);
    }

    System.out.println();
    System.out.println("해결 2: Check-Then-Act 해결 - 잔액 10,000원, 3개 스레드 × 10회 × 1,000원 출금");

    int negativeCount = 0;
    for (int trial = 1; trial <= 10; trial++) {
      Bank bank = new Bank(10_000);
      Thread t1 =
          new Thread(
              () -> {
                for (int i = 0; i < 10; i++) bank.withdraw(1000);
              },
              "출금-A");
      Thread t2 =
          new Thread(
              () -> {
                for (int i = 0; i < 10; i++) bank.withdraw(1000);
              },
              "출금-B");
      Thread t3 =
          new Thread(
              () -> {
                for (int i = 0; i < 10; i++) bank.withdraw(1000);
              },
              "출금-C");
      t1.start();
      t2.start();
      t3.start();
      t1.join();
      t2.join();
      t3.join();

      int finalBalance = bank.getBalance();
      if (finalBalance < 0) {
        negativeCount++;
        System.out.printf("  시도 %2d: 최종 잔액 = %,d원 ← 잔액 음수!%n", trial, finalBalance);
      } else {
        System.out.printf("  시도 %2d: 최종 잔액 = %,d원 [정상 ✓]%n", trial, finalBalance);
      }
    }
    System.out.println("  → 10회 중 잔액 음수 발생: " + negativeCount + "회");

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 파트 2. Semaphore(0) - 신호(Signal) 패턴
    // ─────────────────────────────────────────────────────────────
    System.out.println("[파트 2] Semaphore(0) - 신호 패턴 (한 스레드가 다른 스레드의 완료를 대기)");
    System.out.println("Worker가 작업을 완료하면 signal.release() → Main이 signal.acquire()에서 깨어남");
    System.out.println("(소유권 없음: Worker가 release()로 Main이 기다리는 세마포어를 해제할 수 있다)");
    System.out.println();

    Semaphore signal = new Semaphore(0); // permits=0 → acquire() 즉시 대기

    Thread worker =
        new Thread(
            () -> {
              try {
                System.out.println("  Worker: 데이터 처리 시작...");
                Thread.sleep(1500); // 시간이 걸리는 작업 시뮬레이션
                System.out.println("  Worker: 데이터 처리 완료 → 신호 전송");
                signal.release(); // Main의 acquire() 대기를 깨운다 (소유권 없이 release 가능)
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "Worker");

    worker.start();

    System.out.println("  Main: Worker 완료 대기 중...");
    signal.acquire(); // Worker가 release()할 때까지 대기
    // worker.join(); // Worker가 종료될 때까지 대기
    System.out.println("  Main: 신호 수신 → Worker 결과를 사용해 다음 단계 진행 ✓");

    System.out.println();

    // Semaphore(0) 여러 스레드 순서 제어
    System.out.println("[파트 2-2] Semaphore(0)으로 스레드 실행 순서 제어");
    System.out.println("A 완료 → B 시작 → B 완료 → C 시작 순서 보장");

    Semaphore aToB = new Semaphore(0); // A → B 신호
    Semaphore bToC = new Semaphore(0); // B → C 신호

    Thread threadA =
        new Thread(
            () -> {
              System.out.println("  A: 실행");
              aToB.release(); // B에게 신호
            },
            "A");

    Thread threadB =
        new Thread(
            () -> {
              try {
                aToB.acquire(); // A 완료 대기
                System.out.println("  B: A 완료 후 실행");
                bToC.release(); // C에게 신호
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "B");

    Thread threadC =
        new Thread(
            () -> {
              try {
                bToC.acquire(); // B 완료 대기
                System.out.println("  C: B 완료 후 실행");
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "C");

    // 의도적으로 C → B → A 순서로 시작해도 신호 패턴으로 A→B→C 순서 보장
    threadC.start();
    threadB.start();
    threadA.start();
    threadA.join();
    threadB.join();
    threadC.join();
    System.out.println("  → 시작 순서(C→B→A)와 무관하게 실행 순서(A→B→C) 보장 ✓");
  }
}
