package com.eomcs.cleancode.ch13.exam03;

import java.util.concurrent.Executor;

// 예제 1: SRP - 동시성 관련 코드는 일반 비즈니스 로직과 분리해야 한다
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Order {

    private final boolean valid;

    Order(boolean valid) {
      this.valid = valid;
    }

    boolean isValid() {
      return valid;
    }
  }

  // Bad: 비즈니스 로직과 스레드 생성이 섞여 있다
  //   - 테스트하기 어렵다 (스레드 없이 로직만 테스트 불가)
  //   - 동시성 정책(스레드 풀 등) 변경 시 서비스 코드 수정 필요
  //   - 단일 책임 원칙 위반
  static class BadOrderService {

    public void process(Order order) {
      new Thread(() -> {
        if (order.isValid()) {
          save(order);
        }
      }).start();
    }

    private void save(Order order) {
      System.out.println("save order");
    }
  }

  // Good: 비즈니스 로직만 담당한다
  //   - 스레드 없이 단독으로 테스트 가능하다
  //   - 동시성 정책 변경과 무관하다
  static class OrderService {

    public void process(Order order) {
      if (order.isValid()) {
        save(order);
      }
    }

    private void save(Order order) {
      System.out.println("save order");
    }
  }

  // Good: 동시성 실행만 담당한다
  //   - 비즈니스 로직(OrderService)과 동시성(Executor)이 명확히 분리된다
  //   - Executor를 교체해도 OrderService는 변경 불필요
  static class OrderProcessor {

    private final Executor executor;
    private final OrderService orderService;

    OrderProcessor(Executor executor, OrderService orderService) {
      this.executor = executor;
      this.orderService = orderService;
    }

    public void process(Order order) {
      executor.execute(() -> orderService.process(order));
    }
  }
}
