package com.eomcs.cleancode.ch03.exam03;

import java.util.List;

public class BadAndGood2 {

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

  // Bad: Stepdown Rule 위반
  // - processOrder()가 saveOrder → calculateTotal → printTotal 순서로 호출하지만
  //   함수 정의는 calculateTotal → saveOrder 순서로 호출 순서와 맞지 않는다.
  // - 읽는 사람이 위아래를 오가며 흐름을 추적해야 한다.
  static class BadOrderService {
    Database database = new Database();

    void processOrder(Order order) {
      saveOrder(order);
      int total = calculateTotal(order);
      printTotal(total);
    }

    // 호출 순서와 정의 순서가 불일치 → 흐름이 깨짐
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

  // Good: Stepdown Rule 적용
  // - 함수가 위에서 아래로 읽히며 점점 더 구체적으로 전개된다.
  // - 함수 호출 순서와 함수 정의 순서가 일치해 자연스럽게 읽힌다.
  //
  // processOrder()
  //  ├─ isValidOrder()
  //  ├─ calculateTotal()
  //  │    └─ item.getPrice()
  //  ├─ saveOrder()
  //  └─ printTotal()
  static class GoodOrderService {
    Database database = new Database();

    // 1단계: 고수준 흐름 (진입점)
    void processOrder(Order order) {
      if (isValidOrder(order)) {
        int total = calculateTotal(order);
        saveOrder(order);
        printTotal(total);
      }
    }

    // 2단계: 호출 순서대로 정의
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
