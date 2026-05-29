package com.eomcs.cleancode.ch13.exam02;

// 예제 1: 공유 자원 문제 - 여러 스레드가 같은 데이터를 동시에 접근하면 문제가 발생한다
public class BadAndGood1 {

  private BadAndGood1() {}

  // Bad: count++는 원자적 연산이 아니다
  //   - 실제로는 읽기 → 증가 → 저장의 3단계로 이루어진다
  //   - 여러 스레드가 동시에 실행하면 값이 깨진다
  //   - 예: 두 스레드가 동시에 count=5를 읽고 둘 다 6을 쓰면 증가량이 1이 됨
  static class BadCounter {

    private int count = 0;

    public void increment() {
      count++; // 원자적 연산이 아님
    }

    public int getCount() {
      return count;
    }
  }

  // Good: synchronized로 공유 상태를 보호한다
  //   - 한 번에 하나의 스레드만 increment()를 실행한다
  //   - 읽기 → 증가 → 저장이 하나의 원자적 동작으로 처리된다
  //   - 단, 동기화는 성능 저하와 복잡도를 수반한다
  static class Counter {

    private int count = 0;

    public synchronized void increment() {
      count++;
    }

    public synchronized int getCount() {
      return count;
    }
  }
}
