package com.eomcs.cleancode.ch13.exam09;

import java.util.concurrent.Executor;

// 예제 2: 스레드 없는 코드를 먼저 동작시켜라
// 비즈니스 로직 버그와 동시성 버그를 동시에 잡으려 하지 마라
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Order {

    private final int totalPrice;
    private boolean processed;

    Order(int totalPrice) {
      this.totalPrice = totalPrice;
    }

    int totalPrice() {
      return totalPrice;
    }

    void markProcessed() {
      this.processed = true;
    }

    boolean isProcessed() {
      return processed;
    }
  }

  // Bad: 주문 처리 로직과 스레드 실행이 섞여 있다
  //   - 스레드 없이 비즈니스 로직만 테스트하기 어렵다
  //   - 실패했을 때 비즈니스 버그인지 동시성 버그인지 구분하기 어렵다
  static class BadAsyncOrderProcessor {

    public void process(Order order) {
      new Thread(() -> {
        if (order.totalPrice() > 0) {
          order.markProcessed();
        }
      }).start();
    }
  }

  // Good: 순수 비즈니스 로직만 담당한다
  //   - 스레드 없이 단독으로 테스트 가능하다 (POJO)
  //   - 비즈니스 버그와 동시성 버그를 분리해서 잡을 수 있다
  static class OrderProcessor {

    public void process(Order order) {
      if (order.totalPrice() > 0) {
        order.markProcessed();
      }
    }
  }

  // Good: 동시성 실행만 담당한다
  //   - 비즈니스 로직은 OrderProcessor에 위임한다
  //   - 동시성 정책을 독립적으로 교체할 수 있다
  static class AsyncOrderProcessor {

    private final Executor executor;
    private final OrderProcessor processor;

    AsyncOrderProcessor(Executor executor, OrderProcessor processor) {
      this.executor = executor;
      this.processor = processor;
    }

    public void process(Order order) {
      executor.execute(() -> processor.process(order));
    }
  }
}
