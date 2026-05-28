package com.eomcs.cleancode.ch12.exam05;

import java.util.ArrayList;
import java.util.List;

// 예제 2: 메서드 이름이 의도를 말하게 하라 - OrderProcessor / Order / Item
public class BadAndGood2 {

  private BadAndGood2() {}

  enum OrderStatus { PAID }

  static class Item {

    private final int price;
    private final int quantity;

    Item(int price, int quantity) {
      this.price = price;
      this.quantity = quantity;
    }

    int getPrice() { return price; }
    int getQuantity() { return quantity; }

    int totalPrice() {
      return price * quantity;
    }
  }

  static class Order {

    private final List<Item> items = new ArrayList<>();
    private int totalPrice;
    private OrderStatus status;

    void addItem(Item item) { items.add(item); }
    List<Item> getItems() { return items; }
    boolean isEmpty() { return items.isEmpty(); }

    // bad 버전 API
    void setTotal(int total) { this.totalPrice = total; }
    void setStatus(String status) { this.status = OrderStatus.valueOf(status); }

    // good 버전 API
    void markAsPaid(int totalPrice) {
      this.totalPrice = totalPrice;
      this.status = OrderStatus.PAID;
    }

    int totalPrice() { return totalPrice; }
    OrderStatus status() { return status; }
  }

  // Bad: process()라는 이름이 모호하다
  //   - 주문 검증, 금액 계산, 상태 변경이 섞여 있다
  //   - sum, i, "PAID"의 의미가 약하다
  static class BadOrderProcessor {

    public void process(Order order) {
      if (order.getItems().size() == 0) {
        throw new IllegalArgumentException();
      }

      int sum = 0;
      for (Item i : order.getItems()) {
        sum += i.getPrice() * i.getQuantity();
      }

      order.setTotal(sum);
      order.setStatus("PAID");
    }
  }

  // Good: pay()가 의도를 드러낸다
  //   - 각 단계가 이름으로 설명된다
  //   - Item.totalPrice(), Order.markAsPaid()가 도메인 언어처럼 읽힌다
  static class OrderProcessor {

    public void pay(Order order) {
      validateOrderHasItems(order);

      int totalPrice = calculateTotalPrice(order);

      order.markAsPaid(totalPrice);
    }

    private void validateOrderHasItems(Order order) {
      if (order.isEmpty()) {
        throw new IllegalArgumentException("order must have items");
      }
    }

    private int calculateTotalPrice(Order order) {
      return order.getItems().stream()
          .mapToInt(Item::totalPrice)
          .sum();
    }
  }
}
