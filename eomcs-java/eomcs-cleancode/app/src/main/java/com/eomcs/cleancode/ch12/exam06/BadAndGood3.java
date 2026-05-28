package com.eomcs.cleancode.ch12.exam06;

import java.util.ArrayList;
import java.util.List;

// 예제 3: 불필요한 계층 구조를 만들지 마라 - OrderProcessor
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Order {

    private final List<String> items = new ArrayList<>();
    private boolean processed;

    void addItem(String item) { items.add(item); }
    boolean isEmpty() { return items.isEmpty(); }
    void markAsProcessed() { this.processed = true; }
    boolean isProcessed() { return processed; }
  }

  // Bad: 불필요한 계층 구조
  //   - 구현이 하나뿐인데 인터페이스, 추상 클래스, 구현 클래스가 있다
  //   - 실제 요구사항보다 구조가 앞서 있다
  //   - 단순한 주문 처리가 프레임워크처럼 변했다
  interface BadOrderProcessor {
    void process(Order order);
  }

  static abstract class AbstractOrderProcessor implements BadOrderProcessor {

    @Override
    public void process(Order order) {
      validate(order);
      doProcess(order);
    }

    protected abstract void validate(Order order);
    protected abstract void doProcess(Order order);
  }

  static class DefaultOrderProcessor extends AbstractOrderProcessor {

    @Override
    protected void validate(Order order) {
      if (order.isEmpty()) {
        throw new IllegalArgumentException("empty order");
      }
    }

    @Override
    protected void doProcess(Order order) {
      order.markAsProcessed();
    }
  }

  // Good: 필요한 클래스는 하나로 충분하다
  //   - 흐름이 직접적이다
  //   - 추상화가 실제 필요할 때까지 기다린다
  static class OrderProcessor {

    public void process(Order order) {
      validate(order);
      order.markAsProcessed();
    }

    private void validate(Order order) {
      if (order.isEmpty()) {
        throw new IllegalArgumentException("empty order");
      }
    }
  }
}
