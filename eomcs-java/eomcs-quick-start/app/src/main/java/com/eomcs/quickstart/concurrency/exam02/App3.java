package com.eomcs.quickstart.concurrency.exam02;

// synchronized 블록으로 동기화 문제 해결:
//
// synchronized 블록 문법:
//   synchronized (락 객체) {
//     // 임계 구역(Critical Section): 한 번에 한 스레드만 실행되는 구간
//   }
//
// synchronized 메서드 vs synchronized 블록:
//
//   // synchronized 메서드 (App2 방식)
//   public synchronized void deposit(int amount) {
//     balance += amount; // 메서드 전체가 임계 구역
//   }
//
//   // synchronized 블록 (App3 방식) - 위와 동일한 동작
//   public void deposit(int amount) {
//     synchronized (this) {       // this를 락 객체로 지정
//       balance += amount;        // 블록 내부만 임계 구역
//     }
//   }
//
// synchronized 블록의 장점:
//   1. 락 범위 최소화: 동기화가 필요한 구간만 보호하고,
//      나머지 코드(유효성 검사, 로깅 등)는 락 없이 실행할 수 있다.
//   2. 락 객체 선택: this 외에 별도의 락 객체를 사용할 수 있다.
//      독립적인 공유 자원이 여럿이면 각각 다른 락 객체를 써서
//      불필요한 대기를 줄일 수 있다.
//
// 락 범위 비교:
//   synchronized 메서드 → 메서드 전체
//   synchronized 블록   → 블록 내부만
//   (메서드가 짧으면 실질적인 차이 없음)
//

public class App3 {

  // synchronized 블록으로 동기화를 처리한 계좌 클래스
  static class Bank {

    private int balance; // 잔액
    private final Object lock = new Object(); // 전용 락 객체 (this 대신 사용 가능)

    public Bank(int initialBalance) {
      this.balance = initialBalance;
    }

    // 입금 - synchronized 블록: 읽기-쓰기 구간만 보호
    public void deposit(int amount) {
      // 블록 바깥: 락 없이 실행 (사전 검사, 로깅 등을 여기에 둘 수 있다)
      synchronized (lock) { // lock 객체를 모니터로 사용
        int current = balance; // (1) 잔액 읽기   ← 임계 구역 시작
        Thread.yield(); // 락 보유 중 → 다른 스레드가 이 블록에 진입 불가
        balance = current + amount; // (2) 계산 결과 쓰기 ← 임계 구역 끝
      } // 블록 종료 시 락 자동 해제
    }

    // 출금 - synchronized 블록: 확인(check)과 출금(act)을 하나의 원자적 연산으로 보호
    public void withdraw(int amount) {
      synchronized (lock) { // 입금과 동일한 lock 객체 → 상호 배제 보장
        if (balance >= amount) { // (1) 잔액 확인 (check) ← 임계 구역 시작
          Thread.yield(); // 락 보유 중 → 다른 스레드가 끼어들 수 없음
          balance -= amount; // (2) 출금 (act)        ← 임계 구역 끝
        }
      }
    }

    public int getBalance() {
      return balance;
    }
  }

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // 해결 1. 갱신 분실 해결 - synchronized 블록으로 deposit() 보호
    // ─────────────────────────────────────────────────────────────
    System.out.println("[해결 1] 갱신 분실 해결 - synchronized 블록 deposit()");
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
    // 해결 2. Check-Then-Act 해결 - synchronized 블록으로 withdraw() 보호
    // ─────────────────────────────────────────────────────────────
    System.out.println("\n[해결 2] Check-Then-Act 해결 - synchronized 블록 withdraw()");
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
    // 총 출금액 = 5스레드 × 100회 × 100원 = 50,000원
    // 초기 잔액(100,000원) > 총 출금액(50,000원) → 출금 시 잔액 부족 없음 → 모든 출금 성공
    // 기대 잔액 = 100,000 + 50,000(총 입금) - 50,000(총 출금) = 100,000원
    System.out.println("\n[해결 3] 입금 + 출금 동시 진행 - 잔액 일관성 보장");
    System.out.println("초기 잔액 100,000원, 5개 스레드 입금(100원×100회), 5개 스레드 출금(100원×100회)");
    System.out.println("→ 기대 잔액: 100,000원 (총 입금액 = 총 출금액 = 50,000원)");

    for (int trial = 1; trial <= 3; trial++) {
      Bank bank = new Bank(100_000); // 총 출금액(50,000)의 2배 → 출금 시 잔액 부족 없음

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
      System.out.printf("  시도 %d: 최종 잔액 = %,d원 (기대: 100,000원) [%s]%n", trial, finalBalance, result);
    }

  }
}
