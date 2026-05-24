package com.eomcs.cleancode.ch03.exam02;

import java.util.List;

public class BadAndGood1 {

  static class Item {
    private int price;
    private int quantity;
    Item(int price, int quantity) { this.price = price; this.quantity = quantity; }
    int getPrice() { return price; }
    int getQuantity() { return quantity; }
  }

  static class Order {
    private List<Item> items;
    Order(List<Item> items) { this.items = items; }
    List<Item> getItems() { return items; }
  }

  static class Database {
    void save(Order order) { System.out.println("주문 저장 완료"); }
  }

  // Bad
  // - 하나의 함수가 검증, 계산, 저장, 로깅 등 여러 일을 한다.
  // - 주석으로 단계가 구분된다면 이미 여러 책임이 섞인 것이다 (분리 신호).
  static class BadOrderService {
    Database database = new Database();

    void processOrder(Order order) {
      // 1. 유효성 검사
      if (order == null || order.getItems().isEmpty()) {
        throw new IllegalArgumentException("Invalid order");
      }

      // 2. 총 금액 계산
      int total = 0;
      for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
      }

      // 3. DB 저장
      database.save(order);

      // 4. 로그 출력
      System.out.println("Order processed: " + total);
    }
  }

  // Good
  // - processOrder()는 흐름만 표현하고, 실제 작업은 하위 함수로 위임한다.
  // - 각 함수는 정확히 하나의 일만 수행하며 이름이 역할을 설명한다.
  static class GoodOrderService {
    Database database = new Database();

    void processOrder(Order order) {
      validateOrder(order);
      int total = calculateTotal(order);
      saveOrder(order);
      logOrder(total);
    }

    private void validateOrder(Order order) {
      if (order == null || order.getItems().isEmpty()) {
        throw new IllegalArgumentException("Invalid order");
      }
    }

    private int calculateTotal(Order order) {
      int total = 0;
      for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
      }
      return total;
    }

    private void saveOrder(Order order) {
      database.save(order);
    }

    private void logOrder(int total) {
      System.out.println("Order processed: " + total);
    }
  }
}
