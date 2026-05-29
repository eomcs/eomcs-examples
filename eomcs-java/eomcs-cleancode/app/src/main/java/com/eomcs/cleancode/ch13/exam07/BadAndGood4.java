package com.eomcs.cleancode.ch13.exam07;

import java.util.ArrayList;
import java.util.List;

// 예제 4: 주문 처리 - 외부 API 호출은 락 밖에서 수행하라
public class BadAndGood4 {

  private BadAndGood4() {}

  static class Order {

    private final long id;

    Order(long id) {
      this.id = id;
    }

    long id() {
      return id;
    }
  }

  // Bad: 결제, 배송 같은 외부 작업이 락 안에 있다
  //   - 외부 API 호출이 느리면 전체 처리가 막힌다
  //   - 다른 주문도 대기한다
  static class BadOrderProcessor {

    private final List<Order> processedOrders = new ArrayList<>();

    public synchronized void process(Order order) {
      validate(order);          // 외부 작업 아님이지만 락 불필요
      charge(order);            // 외부 결제 API, 락 불필요
      ship(order);              // 외부 배송 API, 락 불필요

      processedOrders.add(order);  // 실제 임계 구역
    }

    private void validate(Order order) {
      System.out.println("validate: " + order.id());
    }

    private void charge(Order order) {
      System.out.println("charge: " + order.id());
    }

    private void ship(Order order) {
      System.out.println("ship: " + order.id());
    }

    public synchronized List<Order> getProcessedOrders() {
      return new ArrayList<>(processedOrders);
    }
  }

  // Good: 주문 처리 대부분은 락 없이 실행하고, 공유 컬렉션 갱신만 락으로 보호한다
  //   - 락의 범위가 명확해진다
  //   - 결제·배송 작업이 병렬로 진행될 수 있다
  //   - 락 보유 시간이 최소화된다
  static class OrderProcessor {

    private final List<Order> processedOrders = new ArrayList<>();

    public void process(Order order) {
      validate(order);
      charge(order);
      ship(order);

      synchronized (this) {
        processedOrders.add(order);  // 공유 컬렉션 갱신만 보호
      }
    }

    private void validate(Order order) {
      System.out.println("validate: " + order.id());
    }

    private void charge(Order order) {
      System.out.println("charge: " + order.id());
    }

    private void ship(Order order) {
      System.out.println("ship: " + order.id());
    }

    public synchronized List<Order> getProcessedOrders() {
      return new ArrayList<>(processedOrders);
    }
  }
}
