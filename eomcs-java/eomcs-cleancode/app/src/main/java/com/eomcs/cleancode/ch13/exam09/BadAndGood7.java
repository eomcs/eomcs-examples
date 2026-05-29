package com.eomcs.cleancode.ch13.exam09;

import java.util.concurrent.atomic.AtomicInteger;

// 예제 7: 실패가 드러나도록 코드를 계측하라
// yield, sleep 등을 의도적으로 끼워 넣어 스레드 전환 지점을 늘려라
public class BadAndGood7 {

  private BadAndGood7() {}

  // Bad: 실제 버그가 숨어 있을 수 있다
  //   - 읽기와 저장 사이에 다른 스레드가 끼어들 수 있다
  //   - 단순히 실행해서는 race condition이 잘 드러나지 않는다
  static class UnsafeCounter {

    private int count = 0;

    public void increment() {
      int current = count;
      count = current + 1;
    }

    public int value() {
      return count;
    }
  }

  // 테스트용 계측 버전: Thread.yield()로 스레드 전환 지점을 강제로 만든다
  //   - 읽기와 쓰기 사이에 yield()를 끼워 다른 스레드가 끼어들 기회를 만든다
  //   - race condition이 훨씬 잘 드러난다
  //   - 단, 이 코드는 테스트 목적이며 운영에 배포하지 않는다
  static class InstrumentedCounter {

    private int count = 0;

    public void increment() {
      int current = count;
      Thread.yield(); // 일부러 스레드 전환 유도
      count = current + 1;
    }

    public int value() {
      return count;
    }
  }

  // Good: AtomicInteger로 race condition을 근본적으로 해결한다
  static class SafeCounter {

    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
      count.incrementAndGet();
    }

    public int value() {
      return count.get();
    }
  }

  // 자동화된 계측: ThreadJiggle 인터페이스로 전환 지점을 교체 가능하게 만든다
  //   - 테스트에서는 YieldJiggle 또는 SleepJiggle을 주입한다
  //   - 운영 코드에는 NoJiggle을 주입하거나 아예 제거한다
  interface ThreadJiggle {
    void jiggle();
  }

  // 운영용: 아무 동작 없음
  static class NoJiggle implements ThreadJiggle {

    @Override
    public void jiggle() {
    }
  }

  // 테스트용: yield()로 스레드 전환 유도
  static class YieldJiggle implements ThreadJiggle {

    @Override
    public void jiggle() {
      Thread.yield();
    }
  }

  // 테스트용: sleep()으로 전환 기회 강제
  static class SleepJiggle implements ThreadJiggle {

    @Override
    public void jiggle() {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  // ThreadJiggle을 주입받아 테스트 가능한 Counter
  //   - NoJiggle: 운영과 동일한 동작
  //   - YieldJiggle: race condition 노출
  //   - SleepJiggle: 더 긴 지연으로 race condition 노출
  static class TestableCounter {

    private int count = 0;
    private final ThreadJiggle jiggle;

    TestableCounter(ThreadJiggle jiggle) {
      this.jiggle = jiggle;
    }

    public void increment() {
      int current = count;
      jiggle.jiggle();
      count = current + 1;
    }

    public int value() {
      return count;
    }
  }
}
