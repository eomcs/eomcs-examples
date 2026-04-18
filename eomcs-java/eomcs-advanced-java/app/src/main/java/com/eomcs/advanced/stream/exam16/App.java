package com.eomcs.advanced.stream.exam16;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

// 병렬 스트림 기본:
//
// parallelStream()
//   - 컬렉션에서 병렬 스트림을 생성한다.
//   - 내부적으로 ForkJoinPool.commonPool()을 사용한다.
//
// stream().parallel()
//   - 순차 스트림을 병렬 스트림으로 전환한다.
//
// forEach()
//   - 병렬 스트림에서 처리 순서를 보장하지 않는다.
//
// forEachOrdered()
//   - 병렬 처리 후에도 encounter order(원본 순서)를 지켜 출력한다.
//   - 순서 보장 비용 때문에 병렬 처리 이점이 줄어들 수 있다.
//

public class App {

  public static void main(String[] args) {

    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8);

    // ─────────────────────────────────────────────────────────────
    // 예제 1. stream() - 순차 스트림
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 1] stream() - 순차 스트림");

    List<Integer> sequentialResult = numbers.stream()
        .map(n -> trace("순차", n))  // 요소를 처리한 스레드 이름을 출력
        .toList();
    System.out.println("  결과: " + sequentialResult);
    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 2. parallelStream() - 병렬 스트림
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 2] parallelStream() - 병렬 스트림");

    // commonPool의 병렬 처리 수준 = CPU 코어 수 - 1 (호출 스레드 포함 시 코어 수와 동일)
    System.out.println("  commonPool 병렬 처리 수준: " + ForkJoinPool.commonPool().getParallelism());
    List<Integer> parallelResult = numbers.parallelStream()
        .map(n -> trace("병렬", n))  // 여러 스레드가 동시에 처리함을 확인
        .toList();
    System.out.println("  결과: " + parallelResult);
    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 3. stream().parallel() - 기존 순차 스트림을 병렬로 전환
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 3] stream().parallel() - 병렬 전환");

    int sum = numbers.stream()
        .parallel()         // 이 시점부터 병렬 스트림으로 전환
        .mapToInt(n -> n * n)
        .sum();
    System.out.println("  제곱 합계: " + sum);
    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 예제 4. forEach() vs forEachOrdered() - 출력 순서 비교
    // ─────────────────────────────────────────────────────────────
    System.out.println("[예제 4] forEach() vs forEachOrdered()");

    System.out.print("  forEach:        ");
    numbers.parallelStream()
        .forEach(n -> System.out.print(n + " "));  // 처리 순서가 매 실행마다 다를 수 있음
    System.out.println();

    System.out.print("  forEachOrdered: ");
    numbers.parallelStream()
        .forEachOrdered(n -> System.out.print(n + " "));  // 원본 순서(1~8)를 항상 보장
    System.out.println();

    System.out.println();
    System.out.println("→ 병렬 스트림은 ForkJoinPool.commonPool()에서 분할 처리된다.");
    System.out.println("→ forEach()는 처리 순서를 보장하지 않는다. 순서가 필요하면 forEachOrdered()를 사용한다.");
  }

  private static int trace(String label, int n) {
    System.out.printf("  %-2s %d 처리 (%s)%n", label, n, Thread.currentThread().getName());
    sleep(100);
    return n;
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
