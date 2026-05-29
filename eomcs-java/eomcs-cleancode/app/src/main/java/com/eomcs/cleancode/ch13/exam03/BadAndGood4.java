package com.eomcs.cleancode.ch13.exam03;

// 예제 4: 스레드 독립성 - 스레드는 가능한 한 서로 영향을 주지 않도록 설계하라
public class BadAndGood4 {

  private BadAndGood4() {}

  // Bad: 여러 스레드가 공유 필드를 동시에 수정한다
  //   - sharedCounter++는 원자적이지 않다
  //   - 경쟁 조건 발생
  //   - 스레드 간 의존성이 생겨 동기화가 필요해진다
  static class BadTaskProcessor {

    private int sharedCounter = 0;

    public void process() {
      new Thread(() -> sharedCounter++).start();
      new Thread(() -> sharedCounter++).start();
    }

    public int getSharedCounter() {
      return sharedCounter;
    }
  }

  // Good: 각 스레드가 독립적인 지역 변수만 사용한다
  //   - 스레드 간 공유 상태가 없다
  //   - 동기화 필요 없음
  //   - 스레드 수가 늘어도 서로 영향을 주지 않는다
  static class TaskProcessor {

    public void process() {
      new Thread(this::doTask).start();
      new Thread(this::doTask).start();
    }

    private void doTask() {
      int localCounter = 0;
      localCounter++;
      System.out.println("local result: " + localCounter);
    }
  }
}
