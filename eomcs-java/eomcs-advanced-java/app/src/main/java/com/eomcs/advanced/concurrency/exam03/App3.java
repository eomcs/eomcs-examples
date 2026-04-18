package com.eomcs.advanced.concurrency.exam03;

// volatile의 한계 - 원자성(Atomicity) 미보장:
//
// volatile은 가시성(Visibility)만 보장한다.
// count++ 같은 복합 연산(Read-Modify-Write)은 원자적이지 않다.
//
//   count++의 실제 CPU 명령어 순서:
//     (1) 메인 메모리에서 count 읽기   ← volatile이 최신 값 보장
//     (2) 값에 1 더하기
//     (3) 결과를 메인 메모리에 쓰기    ← volatile이 즉시 반영 보장
//
//   두 스레드가 동시에 실행하면:
//     Thread-A: count 읽기 → 100
//     Thread-B: count 읽기 → 100    ← A가 쓰기 전에 읽음
//     Thread-A: 100+1=101, 쓰기 → count=101
//     Thread-B: 100+1=101, 쓰기 → count=101  ← A의 결과 덮어쓰기 (갱신 분실!)
//     기대: 102, 실제: 101
//
// volatile vs synchronized:
//   - volatile: (1)과 (3)에서 메인 메모리 사용을 보장. 하지만 (1)~(3) 전체는 원자적이지 않다.
//   - synchronized: (1)~(3) 전체를 원자적 연산으로 보호한다.
//
// 복합 연산의 해결책:
//   - synchronized 블록: synchronized(lock) { count++; }
//   - AtomicInteger: java.util.concurrent.atomic.AtomicInteger.incrementAndGet()
//

public class App3 {

  // volatile - 가시성만 보장, 원자성은 미보장
  static volatile int counter = 0;

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // 문제: volatile int counter에 count++ → 원자성 미보장 → 갱신 분실
    // ─────────────────────────────────────────────────────────────
    System.out.println("[문제] volatile은 원자성을 보장하지 않는다");
    System.out.println("10개 스레드 × 각 1,000회 counter++ → 기대값: 10,000");

    int threadCount = 10;
    int incrementCount = 10_000;

    for (int trial = 1; trial <= 3; trial++) {
      counter = 0;

      Thread[] threads = new Thread[threadCount];
      for (int i = 0; i < threadCount; i++) {
        threads[i] =
            new Thread(
                () -> {
                  for (int j = 0; j < incrementCount; j++) {
                    counter++; // volatile이지만 원자적이지 않다: 읽기→증가→쓰기 사이 끼어들 수 있음
                  }
                },
                "증가-" + i);
      }

      for (Thread t : threads) t.start();
      for (Thread t : threads) t.join();

      int expected = threadCount * incrementCount;
      String result = (counter == expected) ? "정상" : "오류 ← 갱신 분실 발생!";
      System.out.printf(
          "  시도 %d: counter = %,d (기대: %,d) [%s]%n", trial, counter, expected, result);
    }

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 해결 1: synchronized 블록으로 원자성 보장
    // ─────────────────────────────────────────────────────────────
    System.out.println("[해결 1] synchronized 블록 - 원자성 보장");
    System.out.println("10개 스레드 × 각 1,000회 synchronized { counter++ } → 기대값: 10,000");

    Object lock = new Object();
    int[] syncCounter = {0}; // synchronized 블록 내에서 접근하는 공유 변수

    for (int trial = 1; trial <= 3; trial++) {
      syncCounter[0] = 0;

      Thread[] threads = new Thread[threadCount];
      for (int i = 0; i < threadCount; i++) {
        threads[i] =
            new Thread(
                () -> {
                  for (int j = 0; j < incrementCount; j++) {
                    synchronized (lock) {
                      syncCounter[0]++; // 임계 구역 안: 원자적으로 처리
                    }
                  }
                },
                "증가-" + i);
      }

      for (Thread t : threads) t.start();
      for (Thread t : threads) t.join();

      int expected = threadCount * incrementCount;
      String result = (syncCounter[0] == expected) ? "정상 ✓" : "오류!";
      System.out.printf(
          "  시도 %d: counter = %,d (기대: %,d) [%s]%n", trial, syncCounter[0], expected, result);
    }

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 해결 2: AtomicInteger - 락 없이 원자적 연산
    // ─────────────────────────────────────────────────────────────
    System.out.println("[해결 2] AtomicInteger - 락 없는 원자적 연산");
    System.out.println("10개 스레드 × 각 1,000회 atomicCounter.incrementAndGet() → 기대값: 10,000");

    for (int trial = 1; trial <= 3; trial++) {
      java.util.concurrent.atomic.AtomicInteger atomicCounter =
          new java.util.concurrent.atomic.AtomicInteger(0);

      Thread[] threads = new Thread[threadCount];
      for (int i = 0; i < threadCount; i++) {
        threads[i] =
            new Thread(
                () -> {
                  for (int j = 0; j < incrementCount; j++) {
                    atomicCounter.incrementAndGet(); // CAS(Compare-And-Swap) 연산 → 원자적
                  }
                },
                "증가-" + i);
      }

      for (Thread t : threads) t.start();
      for (Thread t : threads) t.join();

      int expected = threadCount * incrementCount;
      String result = (atomicCounter.get() == expected) ? "정상 ✓" : "오류!";
      System.out.printf(
          "  시도 %d: counter = %,d (기대: %,d) [%s]%n", trial, atomicCounter.get(), expected, result);
    }

    System.out.println();
    System.out.println("정리:");
    System.out.println("  volatile int counter   → 가시성 O, 원자성 X → count++ 갱신 분실");
    System.out.println("  synchronized { count++ } → 가시성 O, 원자성 O → 정확, 락 오버헤드 있음");
    System.out.println("  AtomicInteger           → 가시성 O, 원자성 O → 정확, 락 없어 더 빠름");
  }
}
