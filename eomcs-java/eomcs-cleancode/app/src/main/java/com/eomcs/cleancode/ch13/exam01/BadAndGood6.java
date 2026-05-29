package com.eomcs.cleancode.ch13.exam01;

import java.util.concurrent.CountDownLatch;

// 예제 3: 오해 4 - "동시성을 사용하면 설계가 자동으로 좋아진다"
// 잘못 쓰면 오히려 더 복잡해지고 책임 분리가 흐려진다
public class BadAndGood6 {

  private BadAndGood6() {}

  // Bad: 순서 의존성이 있는 단계를 무작정 별도 스레드로 분리한다
  //   - 순서 보장이 없다
  //   - step1 완료 전에 step2가 실행될 수 있다
  //   - 데이터 공유 문제 발생 가능
  //   - 설계가 오히려 망가질 수 있음
  static class BadPipeline {

    public void process() {
      new Thread(this::step1).start();
      new Thread(this::step2).start();
      new Thread(this::step3).start();
    }

    private void step1() {
      System.out.println("Step 1: 데이터 읽기");
    }

    private void step2() {
      System.out.println("Step 2: 데이터 변환 (step1 완료 여부 불확실)");
    }

    private void step3() {
      System.out.println("Step 3: 데이터 저장 (step2 완료 여부 불확실)");
    }
  }

  // Good: 순서 의존성이 있다면 명시적으로 조율한다
  //   - 단계별 완료를 CountDownLatch로 보장한다
  //   - 실행 순서가 명확하다
  //   - 또는 순서 의존성이 있으면 그냥 순차 처리한다
  static class Pipeline {

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
      System.out.println("Step 1: 데이터 읽기");
    }

    private void step2() {
      System.out.println("Step 2: 데이터 변환");
    }

    private void step3() {
      System.out.println("Step 3: 데이터 저장");
    }
  }
}
