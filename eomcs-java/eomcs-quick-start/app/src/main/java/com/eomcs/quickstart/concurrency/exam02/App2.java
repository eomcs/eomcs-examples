package com.eomcs.quickstart.concurrency.exam02;

// synchronized 키워드로 동기화 문제 해결:
//
// synchronized 메서드:
//   - 메서드 선언에 synchronized를 추가하면 해당 메서드는 한 번에 한 스레드만 실행할 수 있다.
//   - 인스턴스 메서드에 붙이면 해당 객체(this)를 락(monitor)으로 사용한다.
//   - 한 스레드가 synchronized 메서드를 실행 중이면 같은 객체의 다른 synchronized 메서드도
//     다른 스레드가 진입할 수 없다.
//
// synchronized 블록:
//   - synchronized(락 객체) { ... } 형태로 특정 구간만 동기화할 수 있다.
//   - 메서드 전체를 동기화하는 것보다 락 범위를 줄여 성능을 높일 수 있다.
//   - synchronized 메서드는 synchronized(this) { 메서드 전체 } 와 동일하다.
//
// 동기화 동작 원리:
//   - 모니터 락(Monitor Lock): 모든 Java 객체는 내부에 모니터 락을 가진다.
//   - 스레드가 synchronized 진입 시 락을 획득(lock)하고, 종료 시 해제(unlock)한다.
//   - 락이 잠겨 있으면 다른 스레드는 BLOCKED 상태에서 대기한다.
//   - 락 획득·해제는 JVM이 자동으로 처리하며, 예외 발생 시에도 반드시 해제된다.
//
// App(동기화 없음) vs App2(synchronized):
//   - 문제 1 (갱신 분실): synchronized deposit() → 읽기-쓰기가 원자적으로 처리됨
//   - 문제 2 (Check-Then-Act): synchronized withdraw() → 확인-출금이 원자적으로 처리됨
//   - 문제 3 (입금+출금 혼합): 두 메서드 모두 동기화 → 잔액 일관성 보장
//

public class App2 {

  // synchronized로 동기화를 처리한 계좌 클래스
  static class Bank {

    private int balance; // 잔액

    public Bank(int initialBalance) {
      this.balance = initialBalance;
    }

    // 입금 - synchronized 메서드: 한 번에 한 스레드만 진입 가능
    public synchronized void deposit(int amount) {
      int current = balance; // (1) 잔액 읽기
      Thread.yield(); // 스레드 전환 시도 - 하지만 락을 보유 중이므로 다른 스레드가 진입 불가
      balance = current + amount; // (2) 계산 결과 쓰기
    }

    // 출금 - synchronized 메서드: 확인(check)과 출금(act)이 하나의 원자적 연산으로 처리
    public synchronized void withdraw(int amount) {
      if (balance >= amount) { // (1) 잔액 확인 (check)
        Thread.yield(); // 스레드 전환 시도 - 하지만 락을 보유 중이므로 다른 스레드가 진입 불가
        balance -= amount; // (2) 출금 (act)
      }
    }

    public int getBalance() {
      return balance;
    }
  }

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // 해결 1. 갱신 분실 해결 - synchronized deposit()
    // ─────────────────────────────────────────────────────────────
    System.out.println("[해결 1] 갱신 분실 해결 - synchronized deposit()");
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
    // 해결 2. Check-Then-Act 해결 - synchronized withdraw()
    // ─────────────────────────────────────────────────────────────
    System.out.println("\n[해결 2] Check-Then-Act 해결 - synchronized withdraw()");
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
    System.out.println("초기 잔액 10,000원, 5개 스레드 입금(100원×100회), 5개 스레드 출금(100원×100회)");
    System.out.println("→ 기대 잔액: 10,000원 (총 입금액 = 총 출금액)");

    for (int trial = 1; trial <= 3; trial++) {
      Bank bank = new Bank(10_000);

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
      String result = (finalBalance == 10_000) ? "정상 ✓" : "오류!";
      System.out.printf("  시도 %d: 최종 잔액 = %,d원 (기대: 10,000원) [%s]%n", trial, finalBalance, result);
    }

    // ─────────────────────────────────────────────────────────────
    // synchronized 블록 - 메서드 대신 블록으로 동기화 범위 지정
    // ─────────────────────────────────────────────────────────────
    System.out.println("\n[참고] synchronized 블록 - 동기화 범위를 특정 구간으로 한정");

    // synchronized(this) { } 는 synchronized 메서드와 동일하다
    // 락 객체를 별도로 지정하면 여러 락을 독립적으로 관리할 수 있다
    Object lock = new Object();
    int[] sharedValue = {0};

    Thread writer1 =
        new Thread(
            () -> {
              for (int i = 0; i < 1000; i++) {
                synchronized (lock) { // lock 객체를 모니터로 사용
                  sharedValue[0]++;
                }
              }
            },
            "Writer-1");

    Thread writer2 =
        new Thread(
            () -> {
              for (int i = 0; i < 1000; i++) {
                synchronized (lock) { // 같은 lock 객체 → 상호 배제
                  sharedValue[0]++;
                }
              }
            },
            "Writer-2");

    writer1.start();
    writer2.start();
    writer1.join();
    writer2.join();
    System.out.printf(
        "  synchronized 블록 결과: %d (기대: 2000) [%s]%n",
        sharedValue[0], sharedValue[0] == 2000 ? "정상 ✓" : "오류!");
  }
}
