package com.eomcs.cleancode.ch13.exam02;

import java.util.concurrent.atomic.AtomicInteger;

// 예제 6: 성능 vs 안전성 트레이드오프 - 동기화를 하면 안전하지만 성능이 떨어진다
public class BadAndGood6 {

  private BadAndGood6() {}

  // Bad - 안전하지 않음: 동기화 없이 빠르게 실행하지만 경쟁 조건이 발생한다
  //   - 여러 스레드가 동시에 접근하면 값이 깨진다
  static class UnsafeCounter {

    private int count = 0;

    public void increment() {
      count++; // 빠르지만 안전하지 않음
    }

    public int getCount() {
      return count;
    }
  }

  // Bad - 안전하지만 느림: synchronized는 한 번에 하나의 스레드만 허용하여 병목이 생긴다
  //   - 스레드 수가 많을수록 락 경쟁(lock contention)이 심해진다
  static class SlowSafeCounter {

    private int count = 0;

    public synchronized void increment() {
      count++; // 안전하지만 느림
    }

    public synchronized int getCount() {
      return count;
    }
  }

  // Good: AtomicInteger로 안전성과 성능을 함께 확보한다
  //   - CAS(Compare-And-Swap) 연산으로 락 없이 원자성을 보장한다
  //   - synchronized보다 경쟁이 낮을 때 성능이 훨씬 빠르다
  //   - 공유 상태를 최소화하고 불변 연산을 사용하는 것이 최선이다
  static class Counter {

    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
      count.incrementAndGet();
    }

    public int getCount() {
      return count.get();
    }
  }
}
