package com.eomcs.cleancode.ch13.exam08;

// 예제 3: interrupt를 무시하는 나쁜 종료 코드 - InterruptedException을 종료 신호로 다뤄라
public class BadAndGood3 {

  private BadAndGood3() {}

  // Bad: InterruptedException을 잡고 무시한다
  //   - 종료 요청(interrupt)이 와도 루프를 계속 실행한다
  //   - shutdown 코드가 정상적으로 동작하지 않는다
  //   - 스레드가 끝날 방법이 없다
  static class BadWorker implements Runnable {

    @Override
    public void run() {
      while (true) {
        try {
          doWork();
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // 무시 - 종료 신호를 삼켜버린다
        }
      }
    }

    private void doWork() {
      System.out.println("work");
    }
  }

  // Good: interrupt를 종료 신호로 사용하고, 발생 시 상태를 복원한다
  //   - isInterrupted()로 루프 조건을 확인한다
  //   - InterruptedException 발생 시 interrupt 상태를 복원한다
  //   - 복원된 interrupt 상태로 인해 루프가 종료된다
  //   - 정리 코드(cleanup)가 안전하게 실행된다
  static class Worker implements Runnable {

    @Override
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {   // 종료 신호 확인
        try {
          doWork();
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();   // interrupt 상태 복원
        }
      }

      cleanup();   // 루프 종료 후 정리 코드 실행
    }

    private void doWork() {
      System.out.println("work");
    }

    private void cleanup() {
      System.out.println("cleanup");
    }
  }
}
