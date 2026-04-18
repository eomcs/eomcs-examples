package com.eomcs.advanced.concurrency.exam07;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// Work-Stealing:
//
// ForkJoinPool의 worker 스레드는 각자 Deque(덱) 형태의 작업 큐를 가진다.
// 자기 큐의 작업을 처리하다가 할 일이 없어지면, 다른 worker의 큐 끝에서 작업을 훔쳐 온다.
//
// 이 방식은 작업 크기가 균일하지 않아도 worker들이 놀지 않게 해 준다.
// 그래서 재귀적으로 잘게 쪼개지는 분할 정복 작업에 잘 맞는다.
//
// getStealCount():
//   - 다른 worker 큐에서 작업을 훔쳐 온 누적 횟수를 반환한다.
//   - 값이 클수록 worker들이 서로 부하를 잘 분산했다는 의미다.
//   - 단, 이 값은 정밀한 측정치가 아닌 추정치(estimate)임을 기억한다.
//
// 피보나치 수열이 Fork/Join에 적합한 이유:
//   F(n) = F(n-1) + F(n-2)이므로 자연스럽게 두 하위 작업으로 분할된다.
//   다만 실제로는 재귀 호출 수가 지수적으로 늘어나므로, THRESHOLD를 적절히 설정해야 한다.

public class App3 {

  static class FibonacciTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 10;

    private final int n;

    FibonacciTask(int n) {
      this.n = n;
    }

    @Override
    protected Integer compute() {
      // n이 THRESHOLD 이하이면 순차 재귀로 직접 계산한다.
      // - fork 오버헤드 없이 빠르게 처리하는 기저 조건
      if (n <= THRESHOLD) {
        return fibonacci(n);
      }

      // F(n) = F(n-1) + F(n-2) 로 두 하위 작업으로 분할한다.
      FibonacciTask f1 = new FibonacciTask(n - 1);
      FibonacciTask f2 = new FibonacciTask(n - 2);

      // f1을 큐에 넣어 다른 worker가 가져가게 하고,
      // 현재 worker는 f2를 직접 계산한다. (App.java의 left.fork() / right.compute() 패턴과 동일)
      f1.fork();
      int result2 = f2.compute(); // 현재 worker가 직접 계산
      int result1 = f1.join();    // 다른 worker의 결과를 기다린다

      return result1 + result2;
    }

    private int fibonacci(int value) {
      if (value <= 1) {
        return value;
      }
      return fibonacci(value - 1) + fibonacci(value - 2);
    }
  }

  public static void main(String[] args) {

    System.out.println("[Work-Stealing] ForkJoinPool 상태 확인");
    System.out.println("피보나치 계산을 작은 재귀 작업으로 나누어 처리한다.");
    System.out.println();

    // ForkJoinPool(4): 스레드 4개 풀 → 피보나치 재귀가 4개 worker에게 분산된다.
    try (ForkJoinPool pool = new ForkJoinPool(4)) {
      FibonacciTask task = new FibonacciTask(35);

      long start = System.currentTimeMillis();
      int result = pool.invoke(task); // 최상위 작업 제출 + 완료 대기 + 결과 반환
      long elapsed = System.currentTimeMillis() - start;

      System.out.printf("  결과: fibonacci(35) = %,d%n", result);
      System.out.printf("  소요 시간: %,dms%n", elapsed);
      System.out.printf("  병렬 처리 스레드 수: %d%n", pool.getParallelism());
      // getStealCount(): work-steal 횟수 누적값 (추정치)
      // - 값이 크면 worker들이 서로 부하를 잘 분산했다는 의미다.
      System.out.printf("  훔쳐 온 작업 수 추정치: %,d%n", pool.getStealCount());
      System.out.println("→ steal count가 증가했다면 worker들이 서로 작업을 나누어 처리한 것이다.");
    }
  }
}
