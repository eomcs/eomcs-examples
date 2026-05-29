package com.eomcs.cleancode.ch13.exam09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.eomcs.cleancode.ch13.exam09.BadAndGood1.Counter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

// 예제 1: 우연한 실패를 동시성 문제 후보로 보라
// 예제 5: 프로세서 수보다 많은 스레드로 실행하라
// 예제 6: 다른 플랫폼에서 실행하라
class BadAndGood1Test {

  // 예제 1: Good Counter는 어떤 스레드 수에서도 정확한 값을 보장한다
  @Test
  void incrementsFromManyThreads() throws InterruptedException {
    Counter counter = new Counter();

    List<Thread> threads = new ArrayList<>();

    for (int i = 0; i < 1000; i++) {
      Thread thread = new Thread(counter::increment);
      threads.add(thread);
      thread.start();
    }

    for (Thread thread : threads) {
      thread.join();
    }

    assertEquals(1000, counter.value());
  }

  // 예제 5: 프로세서 수보다 많은 스레드로 실행하여 숨은 동시성 문제를 드러낸다
  //   - CPU 코어보다 많은 스레드를 만들면 컨텍스트 스위칭이 자주 발생한다
  //   - 임계 구역 누락, race condition, deadlock이 드러날 가능성이 커진다
  @Test
  void stressTestCounter() throws InterruptedException {
    Counter counter = new Counter();

    int threadCount = Runtime.getRuntime().availableProcessors() * 4;
    int incrementsPerThread = 10_000;

    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    CountDownLatch done = new CountDownLatch(threadCount);

    for (int i = 0; i < threadCount; i++) {
      executor.submit(() -> {
        for (int j = 0; j < incrementsPerThread; j++) {
          counter.increment();
        }
        done.countDown();
      });
    }

    done.await();

    assertEquals(threadCount * incrementsPerThread, counter.value());

    executor.shutdown();
  }

  // 예제 6: 동시성 버그는 타이밍 의존적이므로 반복 실행으로 실패 가능성을 높인다
  //   - 플랫폼마다 스케줄링 타이밍이 다르다
  //   - 내 컴퓨터에서 통과했다고 안전한 것이 아니다
  @Test
  void runManyTimes() throws InterruptedException {
    for (int i = 0; i < 100; i++) {
      stressTestCounter();
    }
  }
}
