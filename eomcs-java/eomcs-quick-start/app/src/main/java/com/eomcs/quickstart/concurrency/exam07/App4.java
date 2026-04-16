package com.eomcs.quickstart.concurrency.exam07;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

// commonPool과 parallelStream:
//
// ForkJoinPool.commonPool()은 JVM 전체에서 공유하는 공용 ForkJoinPool이다.
// parallelStream()은 기본적으로 이 commonPool을 사용한다.
//
// commonPool 병렬도:
//   - 기본값: Runtime.getRuntime().availableProcessors() - 1
//   - JVM 옵션으로 변경 가능: -Djava.util.concurrent.ForkJoinPool.common.parallelism=N
//
// 주의:
//   - commonPool은 애플리케이션 전체에서 공유하므로 직접 shutdown()하지 않는다.
//   - 오래 블로킹되는 I/O 작업을 commonPool에 많이 넣으면 다른 병렬 작업도 밀릴 수 있다.
//   - 스레드 수를 세밀하게 제어해야 한다면 new ForkJoinPool(N)으로 별도 풀을 사용한다.
//
// parallelStream() vs 직렬 stream():
//   stream()        : 단일 스레드로 순서대로 처리한다.
//   parallelStream(): commonPool의 worker 스레드들이 나누어 처리한다.
//   단, 작업 단위가 너무 작거나 순서에 의존하면 오히려 느려질 수 있다.

public class App4 {

  public static void main(String[] args) {

    System.out.println("[commonPool] parallelStream과 ForkJoinPool");
    System.out.println("parallelStream은 기본적으로 ForkJoinPool.commonPool()에서 실행된다.");
    System.out.println();

    // commonPool은 JVM 공용 풀이므로 참조만 가져오며, shutdown()하지 않는다.
    ForkJoinPool commonPool = ForkJoinPool.commonPool();
    // getParallelism(): 사용 가능한 worker 스레드 수 (= CPU 코어 수 - 1, 최소 1)
    System.out.printf("  commonPool 병렬 처리 스레드 수: %d%n", commonPool.getParallelism());
    System.out.println();

    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8);

    // parallelStream(): 내부적으로 ForkJoinPool.commonPool()에서 worker 스레드를 빌려 처리한다.
    // - 출력에서 main 스레드와 ForkJoinPool.commonPool-worker-N 스레드가 함께 나타난다.
    // - I/O 블로킹(sleep)이 많으면 worker가 점유되어 다른 병렬 작업이 밀릴 수 있다.
    int sum =
        numbers.parallelStream()
            .mapToInt(
                n -> {
                  String threadName = Thread.currentThread().getName();
                  System.out.printf("  %s: %d 처리%n", threadName, n);
                  sleep(200); // I/O 대기 시뮬레이션 (commonPool worker를 블로킹)
                  return n * n;
                })
            .sum();

    System.out.printf("%n  제곱 합계: %,d%n", sum);
    System.out.println("→ parallelStream은 편리하지만, 내부에서 공용 ForkJoinPool을 사용한다는 점을 기억해야 한다.");
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
