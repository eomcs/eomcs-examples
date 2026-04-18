package com.eomcs.advanced.concurrency.exam07;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// ForkJoinPool 기본:
//
// ForkJoinPool은 큰 작업을 작은 작업으로 쪼개(fork), 결과를 합치는(join)
// 분할 정복 방식의 병렬 처리에 적합한 ExecutorService 구현체다.
//
// 핵심 흐름:
//   1. 작업 범위가 충분히 작으면 직접 계산한다.
//   2. 작업 범위가 크면 두 개 이상의 작은 작업으로 나눈다.
//   3. fork()로 다른 worker가 처리할 수 있게 작업 큐에 넣는다.
//   4. join()으로 결과를 기다린 뒤 합친다.
//
// RecursiveTask<V>:
//   - 결과를 반환하는 Fork/Join 작업 (결과 없으면 RecursiveAction 사용 → App2 참고)
//
// fork() + compute() + join() 패턴:
//   left.fork();               // 왼쪽을 큐에 넣어 다른 worker가 가져갈 수 있게 한다.
//   long r2 = right.compute(); // 현재 worker는 오른쪽을 직접 계산한다. (스레드 낭비 없음)
//   long r1 = left.join();     // 왼쪽 결과가 준비될 때까지 기다린다.
//
// THRESHOLD (임계값):
//   - 범위가 THRESHOLD 이하일 때 직접 계산으로 전환하는 기준값
//   - 너무 크면 병렬화 부족, 너무 작으면 fork/join 오버헤드 증가
//   - 일반적으로 1,000~10,000 범위에서 실험으로 결정한다.
//
// pool.invoke(task):
//   - 작업 제출 + 완료 대기 + 결과 반환을 한 번에 처리한다.
//   - submit()과 달리 Future 없이 결과를 직접 반환한다.

public class App {

  static class SumTask extends RecursiveTask<Long> {

    private static final int THRESHOLD = 10_000;

    private final int[] numbers;
    private final int start;
    private final int end;

    SumTask(int[] numbers, int start, int end) {
      this.numbers = numbers;
      this.start = start;
      this.end = end;
    }

    @Override
    protected Long compute() {
      int length = end - start;

      // 범위가 THRESHOLD 이하이면 직접 계산 (순차 처리로 전환)
      // - fork/join 오버헤드를 피하기 위한 분할 중단 조건
      if (length <= THRESHOLD) {
        long sum = 0;
        for (int i = start; i < end; i++) {
          sum += numbers[i];
        }
        return sum;
      }

      // 작업을 절반으로 분할한다.
      int mid = start + length / 2;

      SumTask left = new SumTask(numbers, start, mid);
      SumTask right = new SumTask(numbers, mid, end);

      // left.fork(): 왼쪽 작업을 현재 worker의 큐에 넣는다.
      //   - 다른 worker 스레드가 큐에서 작업을 훔쳐(work-steal) 병렬로 처리한다.
      left.fork();
      // right.compute(): 현재 worker가 오른쪽 작업을 직접 재귀 실행한다.
      //   - fork()만 하고 compute()하지 않으면 현재 worker가 놀게 되어 비효율적이다.
      long rightResult = right.compute();
      // left.join(): 왼쪽 작업이 끝날 때까지 대기한다.
      //   - 왼쪽이 이미 완료됐으면 즉시 반환, 아직 진행 중이면 현재 worker가 다른 작업을 처리하며 대기한다.
      long leftResult = left.join();

      return leftResult + rightResult;
    }
  }

  public static void main(String[] args) {

    System.out.println("[기본] RecursiveTask - 배열 합계 계산");
    System.out.println("큰 배열을 작은 범위로 나누어 병렬로 합계를 구한다.");
    System.out.println();

    int[] numbers = new int[1_000_000];
    for (int i = 0; i < numbers.length; i++) {
      numbers[i] = 1;
    }

    // new ForkJoinPool(): 기본 병렬도(CPU 코어 수 - 1)로 풀을 생성한다.
    // - try-with-resources로 사용하면 블록을 벗어날 때 자동으로 shutdown()이 호출된다.
    // - ForkJoinPool.commonPool()은 JVM 공용 풀이며 직접 shutdown하면 안 된다 (App4 참고).
    try (ForkJoinPool pool = new ForkJoinPool()) {
      System.out.printf("  병렬 처리 스레드 수: %d%n", pool.getParallelism());

      SumTask task = new SumTask(numbers, 0, numbers.length);
      // invoke(): 작업을 제출하고 완료될 때까지 현재 스레드(main)를 블로킹한다.
      // - 반환값이 RecursiveTask의 compute() 최종 결과다.
      long result = pool.invoke(task);

      System.out.printf("  합계: %,d%n", result);
      System.out.println("→ RecursiveTask는 여러 하위 작업의 결과를 합쳐 최종 결과를 만든다.");
    }
  }
}
