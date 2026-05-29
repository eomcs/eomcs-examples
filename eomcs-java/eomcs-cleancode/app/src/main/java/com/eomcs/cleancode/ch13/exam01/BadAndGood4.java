package com.eomcs.cleancode.ch13.exam01;

// 예제 3: 오해 2 - "동시성은 항상 간단하다"
// 동기화 없이 공유 데이터를 사용하면 경쟁 상태(Race Condition)가 발생한다
public class BadAndGood4 {

  private BadAndGood4() {}

  // Bad: 동기화 없이 공유 변수를 변경한다
  //   - 여러 스레드가 동시에 접근하면 값이 깨진다
  //   - count++ 는 읽기-수정-쓰기의 세 단계로 이루어져 원자적이지 않다
  static class BadCounter {

    private int count = 0;

    public void increment() {
      count++; // race condition 발생 가능
    }

    public int getCount() {
      return count;
    }
  }

  // Good: synchronized로 임계 영역을 보호한다
  //   - 동기화가 필요하다
  //   - 한 번에 하나의 스레드만 increment()를 실행할 수 있다
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
