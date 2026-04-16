package com.eomcs.quickstart.concurrency.exam10;

import java.util.ArrayList;
import java.util.List;

// 많은 가상 스레드 실행:
//
// 플랫폼 스레드는 OS 스레드와 연결되므로 수천~수만 개를 만들면 비용이 크다.
// 가상 스레드는 훨씬 가볍기 때문에 많은 블로킹 작업을 단순한 코드로 처리하기 좋다.
//
// 1,000개 작업이 각각 200ms 대기 → 플랫폼 스레드 풀이면 수십 초 걸리지만,
// 가상 스레드는 1,000개가 거의 동시에 시작하므로 약 200ms 만에 완료된다.
//
// 가상 스레드가 빠른 이유:
//   - sleep() 같은 블로킹 시점에 캐리어 스레드에서 자동으로 unmount된다.
//   - 캐리어 스레드는 다른 가상 스레드를 실행하며 놀지 않는다.
//   - 1,000개가 동시에 대기해도 캐리어 스레드는 CPU 코어 수만큼만 있으면 된다.
//
// 가상 스레드가 빠르지 않은 경우:
//   - CPU 연산이 많은 작업: 가상 스레드가 많아도 코어 수가 병목이다.
//   - synchronized 블록 안에서 블로킹: 캐리어 스레드가 고정(pinning)되어 효과가 반감된다.
//     (Java 21에서는 pinning 경고 로그가 출력된다. Java 24+에서는 개선됨)

public class App2 {

  public static void main(String[] args) throws InterruptedException {

    System.out.println("[대량 실행] 많은 가상 스레드 생성");
    System.out.println("1,000개 작업이 각각 200ms 동안 대기한다.");
    System.out.println();

    int taskCount = 1_000;
    List<Thread> threads = new ArrayList<>();

    long start = System.currentTimeMillis();

    // 1,000개 가상 스레드를 즉시 생성하고 시작한다.
    // - 플랫폼 스레드라면 OS 자원 부족으로 OutOfMemoryError가 발생할 수 있다.
    // - 가상 스레드는 JVM 힙에서 가볍게 생성되므로 수백만 개도 가능하다.
    for (int i = 0; i < taskCount; i++) {
      final int taskNo = i;
      Thread thread =
          Thread.startVirtualThread(
              () -> {
                // sleep(): 가상 스레드가 블로킹되면 캐리어 스레드에서 unmount된다.
                // - 1,000개가 동시에 sleep해도 캐리어 스레드는 소수(CPU 코어 수)만 점유한다.
                sleep(200);
                if (taskNo % 250 == 0) { // 250개마다 진행 상황 출력
                  System.out.printf("  작업-%04d 완료 (%s)%n", taskNo, Thread.currentThread());
                }
              });
      threads.add(thread);
    }

    // 모든 가상 스레드가 완료될 때까지 대기한다.
    for (Thread thread : threads) {
      thread.join();
    }

    long elapsed = System.currentTimeMillis() - start;
    System.out.printf("%n  전체 완료 시간: %,dms%n", elapsed);
    System.out.println("→ 대기 시간이 많은 작업을 많은 가상 스레드로 단순하게 표현할 수 있다.");
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
