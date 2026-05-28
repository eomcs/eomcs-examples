package com.eomcs.cleancode.ch12.exam04;

import java.util.ArrayList;
import java.util.List;

// 예제 3: 계산식 중복을 제거하라 - OrderService / Item
public class BadAndGood3 {

  private BadAndGood3() {}

  static class BadItem {

    private final int price;
    private final int quantity;

    BadItem(int price, int quantity) {
      this.price = price;
      this.quantity = quantity;
    }

    int getPrice() { return price; }
    int getQuantity() { return quantity; }
  }

  static class BadOrder {

    private final List<BadItem> items = new ArrayList<>();

    void addItem(BadItem item) { items.add(item); }
    List<BadItem> getItems() { return items; }
  }

  static class BadCart {

    private final List<BadItem> items = new ArrayList<>();

    void addItem(BadItem item) { items.add(item); }
    List<BadItem> getItems() { return items; }
  }

  // Bad: item.getPrice() * item.getQuantity()가 반복된다
  //   - 합계 계산 방식도 반복된다
  //   - 가격 계산 규칙이 바뀌면 여러 곳을 수정해야 한다
  static class BadOrderService {

    public int calculateOrderTotal(BadOrder order) {
      return order.getItems().stream()
          .mapToInt(item -> item.getPrice() * item.getQuantity())
          .sum();
    }

    public int calculateCartTotal(BadCart cart) {
      return cart.getItems().stream()
          .mapToInt(item -> item.getPrice() * item.getQuantity())
          .sum();
    }
  }

  // Good: Item.totalPrice()라는 개념이 드러난다
  //   - 중복 제거가 곧 설계 개선으로 이어진다
  //   - 계산 규칙 변경 지점이 하나로 줄어든다
  static class Item {

    private final int price;
    private final int quantity;

    Item(int price, int quantity) {
      this.price = price;
      this.quantity = quantity;
    }

    int totalPrice() {
      return price * quantity;
    }
  }

  static class Order {

    private final List<Item> items = new ArrayList<>();

    void addItem(Item item) { items.add(item); }
    List<Item> getItems() { return items; }
  }

  static class Cart {

    private final List<Item> items = new ArrayList<>();

    void addItem(Item item) { items.add(item); }
    List<Item> getItems() { return items; }
  }

  static class OrderService {

    public int calculateOrderTotal(Order order) {
      return calculateTotal(order.getItems());
    }

    public int calculateCartTotal(Cart cart) {
      return calculateTotal(cart.getItems());
    }

    private int calculateTotal(List<Item> items) {
      return items.stream()
          .mapToInt(Item::totalPrice)
          .sum();
    }
  }
}
