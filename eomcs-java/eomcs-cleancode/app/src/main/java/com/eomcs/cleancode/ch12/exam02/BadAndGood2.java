package com.eomcs.cleancode.ch12.exam02;

import java.util.ArrayList;
import java.util.List;

// 예제 2: 테스트가 "올바른 계산 규칙"을 정의한다 - OrderService
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Item {

    private final int price;
    private final int quantity;

    Item(int price, int quantity) {
      this.price = price;
      this.quantity = quantity;
    }

    int getPrice() { return price; }
    int getQuantity() { return quantity; }
  }

  static class Order {

    private final List<Item> items = new ArrayList<>();

    void addItem(Item item) {
      items.add(item);
    }

    List<Item> getItems() { return items; }
  }

  // Bad: 테스트가 없으면 이 버그는 쉽게 놓친다
  //   - 수량을 고려하지 않는다
  //   - 이후 리팩토링하면 더 위험해진다
  static class BadOrderService {

    public int totalPrice(Order order) {
      int total = 0;

      for (Item item : order.getItems()) {
        total += item.getPrice(); // 수량 고려 안 함 (버그!)
      }

      return total;
    }
  }

  // Good: 테스트가 "올바른 계산 규칙"을 정의한다
  //   - 이후 구조를 바꿔도 테스트가 보호해준다
  static class OrderService {

    public int totalPrice(Order order) {
      return order.getItems().stream()
          .mapToInt(item -> item.getPrice() * item.getQuantity())
          .sum();
    }
  }
}
