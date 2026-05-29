package com.eomcs.cleancode.ch13.exam09;

import com.eomcs.cleancode.ch13.exam09.BadAndGood7.TestableCounter;
import com.eomcs.cleancode.ch13.exam09.BadAndGood7.YieldJiggle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

// 예제 7: 실패가 드러나도록 코드를 계측하라
// YieldJiggle로 스레드 전환 지점을 강제하여 race condition을 노출한다
class BadAndGood7Test {

  // YieldJiggle을 주입한 TestableCounter는 race condition이 훨씬 잘 드러난다
  //   - 이 테스트는 의도적으로 race condition을 유발하여 실패를 확인한다
  //   - 실패하면 동기화 없이는 안전하지 않다는 것을 증명한다
  //   - 운영 코드는 SafeCounter(AtomicInteger)를 사용한다
  @Test
  void exposesRaceConditionWithJiggle() throws InterruptedException {
    TestableCounter counter = new TestableCounter(new YieldJiggle());

    int threadCount = 20;
    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    CountDownLatch done = new CountDownLatch(threadCount);

    for (int i = 0; i < threadCount; i++) {
      executor.submit(() -> {
        counter.increment();
        done.countDown();
      });
    }

    done.await();

    // 이 어서션은 race condition 때문에 실패할 수 있다
    // 실패 자체가 동시성 버그가 존재함을 증명하는 것이 이 테스트의 목적이다
    System.out.println("expected: " + threadCount + ", actual: " + counter.value());

    executor.shutdown();
  }
}
