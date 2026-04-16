package com.eomcs.quickstart.concurrency.exam02;

// 동기화 없는 멀티스레드 문제점:
//
// 경쟁 조건(Race Condition):
//   - 두 스레드가 공유 자원을 동시에 읽고 쓸 때 실행 순서에 따라 결과가 달라지는 현상이다.
//   - balance를 읽고(read) → 계산하고(compute) → 쓰는(write) 3단계 사이에
//     다른 스레드가 끼어들면 변경 내용이 덮어써져 사라진다(갱신 분실, Lost Update).
//
// 확인-후-행동(Check-Then-Act) 문제:
//   - 조건을 확인(check)하고 행동(act)하는 사이에 다른 스레드가 상태를 바꾸면
//     확인 시점의 조건이 무효가 된 채 행동이 실행된다.
//   - 잔액을 확인 후 출금하는 사이에 다른 스레드가 먼저 출금하면
//     잔액이 음수가 되는 상황이 발생할 수 있다.
//
// 원인:
//   - balance++ 또는 balance += amount 같은 연산이 겉으로는 한 줄이지만,
//     CPU 명령어 수준에서는 "읽기 → 더하기 → 쓰기" 세 단계로 나뉜다.
//   - 두 스레드가 이 세 단계 사이에 번갈아 실행되면 한 스레드의 쓰기가
//     다른 스레드의 쓰기를 덮어쓴다.
//
// 예시 (balance = 100, 두 스레드가 각각 10을 입금):
//   Thread-A: balance 읽기 → 100
//   Thread-B: balance 읽기 → 100       ← A의 읽기와 겹침
//   Thread-A: 100 + 10 = 110, 쓰기 → balance = 110
//   Thread-B: 100 + 10 = 110, 쓰기 → balance = 110  ← A의 결과 덮어쓰기
//   최종 balance = 110 (기대값 120, 10이 사라짐 → 갱신 분실)
//

public class App {

  // 동기화 처리가 없는 계좌 클래스
  static class Bank {

    private int balance; // 잔액

    public Bank(int initialBalance) {
      this.balance = initialBalance;
    }

    // 입금 - 동기화 없음
    public void deposit(int amount) {
      int current = balance; // (1) 현재 잔액 읽기
      // 스레드 전환이 여기서 발생하면 다른 스레드도 같은 current를 읽는다
      Thread.yield(); // 스레드 전환을 유도하여 경쟁 조건을 쉽게 재현
      balance = current + amount; // (2) 계산 결과 쓰기 (다른 스레드의 쓰기를 덮어쓸 수 있음)
    }

    // 출금 - 동기화 없음
    public void withdraw(int amount) {
      if (balance >= amount) { // (1) 잔액 확인 (check)
        Thread.yield(); // 스레드 전환을 유도하여 경쟁 조건을 쉽게 재현
        balance -= amount; // (2) 출금 (act) - 잔액이 이미 부족해졌을 수 있음
      }
    }

    public int getBalance() {
      return balance;
    }
  }

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // 문제 1. 갱신 분실(Lost Update) - 동시 입금 시 잔액 오류
    // ─────────────────────────────────────────────────────────────
    System.out.println("[문제 1] 갱신 분실 - 동시 입금");
    System.out.println("10개 스레드 × 각 1,000회 입금(1원) → 기대 잔액: 10,000원");

    int threadCount = 10;
    int depositCount = 1_000;

    // 같은 실험을 여러 번 반복하여 결과 불일치를 확인한다
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
      String result = (finalBalance == threadCount * depositCount) ? "정상" : "오류 ← 갱신 분실 발생!";
      System.out.printf("  시도 %d: 최종 잔액 = %,d원 [%s]%n", trial, finalBalance, result);
    }

    // ─────────────────────────────────────────────────────────────
    // 문제 2. Check-Then-Act - 동시 출금 시 잔액 음수
    // ─────────────────────────────────────────────────────────────
    System.out.println("\n[문제 2] Check-Then-Act - 동시 출금 시 잔액 음수");
    System.out.println("잔액 1,000원, 두 스레드가 동시에 800원 출금 시도");
    System.out.println("→ 각 스레드가 잔액 확인 시 1,000원을 보고 출금하면 잔액이 음수가 된다");

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
        System.out.printf(
            "  시도 %2d: 최종 잔액 = %,d원 ← 잔액 음수! (Check-Then-Act 실패)%n", trial, finalBalance);
      } else {
        System.out.printf("  시도 %2d: 최종 잔액 = %,d원%n", trial, finalBalance);
      }
    }
    System.out.println("  → 10회 중 잔액 음수 발생: " + negativeCount + "회");

    // ─────────────────────────────────────────────────────────────
    // 문제 3. 입금 + 출금 동시 진행 - 잔액 불일치
    // ─────────────────────────────────────────────────────────────
    System.out.println("\n[문제 3] 입금 + 출금 동시 진행 - 잔액 불일치");
    System.out.println("초기 잔액 0원, 5개 스레드 입금(100원×100회), 5개 스레드 출금(100원×100회)");
    System.out.println("→ 기대 잔액: 0원 (총 입금 = 총 출금)");

    for (int trial = 1; trial <= 3; trial++) {
      Bank bank = new Bank(10_000); // 출금 스레드가 잔액 부족을 피하도록 초기값 설정

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
      // 기대값: 10,000원 (입금 합계 = 출금 합계이므로 초기값 유지)
      String result = (finalBalance == 10_000) ? "정상" : "오류 ← 잔액 불일치!";
      System.out.printf("  시도 %d: 최종 잔액 = %,d원 (기대: 10,000원) [%s]%n", trial, finalBalance, result);
    }

    System.out.println("\n→ 위 문제들은 synchronized 키워드 또는 java.util.concurrent.atomic 패키지로 해결할 수 있다.");
  }
}
