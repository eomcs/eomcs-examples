package com.eomcs.cleancode.ch13.exam09;

import java.util.concurrent.atomic.AtomicInteger;

// 예제 1: 우연한 실패를 동시성 문제 후보로 보라
// "가끔 한 번 실패했지만 다시 돌리니 통과했다"를 무시하지 마라
public class BadAndGood1 {

  private BadAndGood1() {}

  // Bad: count++는 원자적 연산이 아니다
  //   - 읽기 → 증가 → 저장의 3단계로 이루어진다
  //   - 여러 스레드가 동시에 실행하면 간헐적으로 값이 깨진다
  //   - 가끔 1000이 나오지만 때로는 997, 998처럼 실패한다
  //   - 다시 실행해서 통과했다고 무시하면 안 된다 - race condition 후보다
  static class BadCounter {

    private int count = 0;

    public void increment() {
      count++;
    }

    public int value() {
      return count;
    }
  }

  // Good: AtomicInteger는 CAS(Compare-And-Swap)로 원자성을 보장한다
  //   - 스레드 수와 무관하게 항상 정확한 값을 반환한다
  //   - 간헐적 실패가 사라진다
  static class Counter {

    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
      count.incrementAndGet();
    }

    public int value() {
      return count.get();
    }
  }
}
