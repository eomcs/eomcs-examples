package com.eomcs.advanced.stream.exam16;

import java.util.stream.LongStream;

// 병렬 스트림 성능:
//
// 병렬 스트림은 항상 빠른 것이 아니다.
//
// 병렬화가 유리한 경우:
//   - 요소 수가 많다.
//   - 각 요소 처리 비용이 충분히 크다.
//   - 작업을 독립적으로 나눌 수 있다.
//   - 순서 보장이 꼭 필요하지 않다.
//
// 병렬화가 불리한 경우:
//   - 요소 수가 적다.
//   - 요소 하나의 처리 비용이 작다.
//   - 공유 상태를 자주 갱신한다.
//   - 순서 보장, 동기화, 박싱/언박싱 비용이 크다.
//

public class App2 {

  public static void main(String[] args) {

    long count = 200_000;

    // ─────────────────────────────────────────────────────────────
    // 예제 1. 단순 계산 - 병렬화 오버헤드가 더 클 수 있음
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] 작은 작업 - 병렬화 오버헤드 비교");

    long start = System.currentTimeMillis();
    long seqSum = LongStream.rangeClosed(1, count)
        .map(n -> n * 2)   // 단순 곱셈 - 요소별 처리 비용이 매우 작음
        .sum();
    long seqTime = System.currentTimeMillis() - start;

    start = System.currentTimeMillis();
    long parallelSum = LongStream.rangeClosed(1, count)
        .parallel()        // 분할/스케줄링/병합 오버헤드가 발생
        .map(n -> n * 2)
        .sum();
    long parallelTime = System.currentTimeMillis() - start;

    System.out.printf("  순차 합계: %,d (%,dms)%n", seqSum, seqTime);
    System.out.printf("  병렬 합계: %,d (%,dms)%n", parallelSum, parallelTime);
    System.out.println("  → 단순 계산은 병렬화 비용이 더 클 수 있다.");
    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. 무거운 작업 - 병렬 처리 이점이 드러남
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] 무거운 작업 - 병렬 처리 효과");

    long heavyCount = 4_000;

    start = System.currentTimeMillis();
    long sequential = LongStream.rangeClosed(1, heavyCount)
        .map(App2::heavyWork)  // 요소 하나당 3,000번 반복 연산 수행
        .sum();
    seqTime = System.currentTimeMillis() - start;

    start = System.currentTimeMillis();
    long parallel = LongStream.rangeClosed(1, heavyCount)
        .parallel()            // 무거운 작업을 여러 스레드에 분산
        .map(App2::heavyWork)
        .sum();
    parallelTime = System.currentTimeMillis() - start;

    System.out.printf("  순차 결과: %,d (%,dms)%n", sequential, seqTime);
    System.out.printf("  병렬 결과: %,d (%,dms)%n", parallel, parallelTime);
    System.out.println();
    System.out.println("→ 병렬 스트림은 요소별 작업 비용이 충분히 클 때 효과가 나타난다.");
  }

  private static long heavyWork(long value) {
    long result = value;
    for (int i = 0; i < 3_000; i++) {
      result = (result * 31 + i) % 1_000_003;
    }
    return result;
  }
}
