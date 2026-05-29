package com.eomcs.cleancode.ch13.exam02;

import java.util.concurrent.CountDownLatch;

// 예제 7: 설계 복잡도 증가 - 동시성은 설계를 비선형적으로 복잡하게 만든다
public class BadAndGood7 {

  private BadAndGood7() {}

  // Bad: 순서 의존성이 있는 단계를 아무 제어 없이 병렬로 실행한다
  //   - 실행 순서 보장 없음
  //   - step2가 step1 완료 전에 실행될 수 있다
  //   - 디버깅 어려움
  //   - 코드가 짧아도 이해와 검증이 어렵다
  static class BadProcessor {

    public void process() {
      new Thread(this::step1).start();
      new Thread(this::step2).start();
      new Thread(this::step3).start();
    }

    private void step1() {
      System.out.println("Step 1");
    }

    private void step2() {
      System.out.println("Step 2");
    }

    private void step3() {
      System.out.println("Step 3");
    }
  }

  // Good: 순서 의존성이 있다면 명시적으로 제어한다
  //   - CountDownLatch로 단계 간 완료를 보장한다
  //   - 실행 흐름이 코드에서 명확히 드러난다
  //   - 병렬성이 필요 없다면 순차 실행이 가장 단순하고 안전하다
  static class Processor {

    public void process() throws InterruptedException {
      CountDownLatch step1Done = new CountDownLatch(1);
      CountDownLatch step2Done = new CountDownLatch(1);

      Thread t1 = new Thread(() -> {
        step1();
        step1Done.countDown();
      });

      Thread t2 = new Thread(() -> {
        try {
          step1Done.await();
          step2();
          step2Done.countDown();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      });

      Thread t3 = new Thread(() -> {
        try {
          step2Done.await();
          step3();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      });

      t1.start();
      t2.start();
      t3.start();
    }

    private void step1() {
      System.out.println("Step 1");
    }

    private void step2() {
      System.out.println("Step 2");
    }

    private void step3() {
      System.out.println("Step 3");
    }
  }
}
