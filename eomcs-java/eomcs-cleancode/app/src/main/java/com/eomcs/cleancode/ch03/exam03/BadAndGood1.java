package com.eomcs.cleancode.ch03.exam03;

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
    boolean isValid() { return items != null && !items.isEmpty(); }
  }

  static class Database {
    void save(Order order) { System.out.println("주문 저장 완료"); }
  }

  // Bad
  // - 추상화 수준이 한 함수에 혼합되어 있다.
  //   - order.isValid()             → 비즈니스 로직 (고수준)
  //   - for 반복문 + 가격 계산       → 구현 디테일  (저수준)
  //   - database.save, println      → 인프라 코드  (저수준)
  // - "무엇을 하는지"와 "어떻게 하는지"가 동시에 등장해 읽기 어렵다.
  static class BadOrderService {
    Database database = new Database();

    void processOrder(Order order) {
      if (order.isValid()) {
        int total = 0;
        for (Item item : order.getItems()) {
          total += item.getPrice() * item.getQuantity();
        }
        database.save(order);
        System.out.println("Total: " + total);
      }
    }
  }

  // Good
  // - processOrder()는 같은 수준의 추상화(고수준)만 포함한다.
  // - "무엇을 하는지"만 표현하고, "어떻게 하는지"는 각 하위 함수가 담당한다.
  static class GoodOrderService {
    Database database = new Database();

    // 고수준: 흐름만 표현
    void processOrder(Order order) {
      if (isValidOrder(order)) {
        int total = calculateTotal(order);
        saveOrder(order);
        printTotal(total);
      }
    }

    // 저수준: 구현을 담당
    private boolean isValidOrder(Order order) {
      return order != null && !order.getItems().isEmpty();
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

    private void printTotal(int total) {
      System.out.println("Total: " + total);
    }
  }
}
