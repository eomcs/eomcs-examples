package com.eomcs.advanced.concurrency.exam04;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// ReentrantLock 기본 사용:
//
// java.util.concurrent.locks.Lock 인터페이스는 synchronized보다 유연한 락을 제공한다.
// ReentrantLock은 Lock 인터페이스의 대표적인 구현체이며, synchronized와 동일한 상호 배제를 보장한다.
//
// 핵심 사용 패턴:
//   lock.lock();
//   try {
//     // 임계 구역
//   } finally {
//     lock.unlock(); // 예외가 발생해도 반드시 해제
//   }
//
//   - lock()은 반드시 try 블록 바깥에서 호출한다.
//     try 안에서 호출하면 lock() 자체가 예외를 던질 때 finally의 unlock()이 실행되어
//     잠기지 않은 락을 해제하는 오류가 생긴다.
//   - finally에서 unlock()을 호출해야 예외 발생 시에도 락이 반드시 해제된다.
//     이를 지키지 않으면 데드락이 발생할 수 있다.
//
// synchronized vs ReentrantLock:
//
//   // synchronized 방식
//   public synchronized void deposit(int amount) { ... }
//
//   // ReentrantLock 방식 - 위와 동일한 상호 배제 보장
//   private final Lock lock = new ReentrantLock();
//   public void deposit(int amount) {
//     lock.lock();
//     try { ... } finally { lock.unlock(); }
//   }
//
// ReentrantLock 추가 기능 (synchronized에 없는 기능):
//   - tryLock()           : 락 획득 실패 시 즉시 반환 (비차단)  → App2 참고
//   - tryLock(t, unit)    : 타임아웃 있는 락 획득 시도           → App2 참고
//   - lockInterruptibly() : 락 대기 중 인터럽트 가능             → App2 참고
//   - new ReentrantLock(true): 공정 락 (오래 기다린 스레드 우선)
//   - lock.newCondition() : 조건 변수 (wait/notify 대체)         → App4 참고
//

public class App {

  // ReentrantLock으로 동기화를 처리한 계좌 클래스
  static class Bank {

    private int balance; // 잔액
    private final Lock lock = new ReentrantLock(); // 전용 락

    public Bank(int initialBalance) {
      this.balance = initialBalance;
    }

    // 입금 - lock/unlock으로 임계 구역 보호
    public void deposit(int amount) {
      lock.lock(); // 락 획득 (다른 스레드가 락을 보유 중이면 대기)
      try {
        int current = balance; // (1) 잔액 읽기   ← 임계 구역 시작
        Thread.yield(); // 락 보유 중 → 다른 스레드가 임계 구역에 진입 불가
        balance = current + amount; // (2) 계산 결과 쓰기 ← 임계 구역 끝
      } finally {
        lock.unlock(); // 예외 발생 시에도 반드시 락 해제
      }
    }

    // 출금 - 확인(check)과 출금(act)을 하나의 원자적 연산으로 보호
    public void withdraw(int amount) {
      lock.lock();
      try {
        if (balance >= amount) { // (1) 잔액 확인 (check) ← 임계 구역 시작
          Thread.yield(); // 락 보유 중 → 다른 스레드가 끼어들 수 없음
          balance -= amount; // (2) 출금 (act)         ← 임계 구역 끝
        }
      } finally {
        lock.unlock();
      }
    }

    public int getBalance() {
      return balance;
    }
  }

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // 해결 1. 갱신 분실 해결 - ReentrantLock으로 deposit() 보호
    // ─────────────────────────────────────────────────────────────
    System.out.println("[해결 1] 갱신 분실 해결 - ReentrantLock deposit()");
    System.out.println("10개 스레드 × 각 1,000회 입금(1원) → 기대 잔액: 10,000원");

    int threadCount = 10;
    int depositCount = 1_000;

    for (int trial = 1; trial <= 3; trial++) {
      Bank bank = new Bank(0);

      Thread[] threads = new Thread[threadCount];
      for (int i = 0; i < threadCount; i++) {
        threads[i] =
            new Thread(
                () -> {
                  for (int j = 0; j < depositCount; j++) {
                    bank.deposit(1);
                  }
                },
                "입금-" + i);
      }

      for (Thread t : threads) t.start();
      for (Thread t : threads) t.join();

      int finalBalance = bank.getBalance();
      String result = (finalBalance == threadCount * depositCount) ? "정상 ✓" : "오류!";
      System.out.printf("  시도 %d: 최종 잔액 = %,d원 [%s]%n", trial, finalBalance, result);
    }

    // ─────────────────────────────────────────────────────────────
    // 해결 2. Check-Then-Act 해결 - ReentrantLock으로 withdraw() 보호
    // ─────────────────────────────────────────────────────────────
    System.out.println("\n[해결 2] Check-Then-Act 해결 - ReentrantLock withdraw()");
    System.out.println("잔액 10,000원, 3개 스레드가 각 10회 × 1,000원 출금 시도");
    System.out.println("→ 잔액이 음수가 되지 않아야 한다");

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

    // ─────────────────────────────────────────────────────────────
    // 해결 3. 입금 + 출금 동시 진행 - 잔액 일관성 보장
    // ─────────────────────────────────────────────────────────────
    System.out.println("\n[해결 3] 입금 + 출금 동시 진행 - 잔액 일관성 보장");
    System.out.println("초기 잔액 100,000원, 5개 스레드 입금(100원×100회), 5개 스레드 출금(100원×100회)");
    System.out.println("→ 기대 잔액: 100,000원 (총 입금액 = 총 출금액 = 50,000원)");

    for (int trial = 1; trial <= 3; trial++) {
      Bank bank = new Bank(100_000);

      Thread[] depositors = new Thread[5];
      Thread[] withdrawers = new Thread[5];

      for (int i = 0; i < 5; i++) {
        depositors[i] =
            new Thread(
                () -> {
                  for (int j = 0; j < 100; j++) bank.deposit(100);
                },
                "입금-" + i);
        withdrawers[i] =
            new Thread(
                () -> {
                  for (int j = 0; j < 100; j++) bank.withdraw(100);
                },
                "출금-" + i);
      }

      for (Thread t : depositors) t.start();
      for (Thread t : withdrawers) t.start();
      for (Thread t : depositors) t.join();
      for (Thread t : withdrawers) t.join();

      int finalBalance = bank.getBalance();
      String result = (finalBalance == 100_000) ? "정상 ✓" : "오류!";
      System.out.printf(
          "  시도 %d: 최종 잔액 = %,d원 (기대: 100,000원) [%s]%n", trial, finalBalance, result);
    }
  }
}
