package com.eomcs.cleancode.ch12.exam03;

import java.util.ArrayList;
import java.util.List;

// 예제 1: 중복을 제거하라 - OrderService / Item
public class BadAndGood1 {

  private BadAndGood1() {}

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
  //   - 합계 계산 로직도 반복된다
  //   - 가격 계산 규칙이 바뀌면 여러 곳을 수정해야 한다
  static class BadOrderService {

    public int calculateOrderTotal(BadOrder order) {
      int total = 0;
      for (BadItem item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
      }
      return total;
    }

    public int calculateCartTotal(BadCart cart) {
      int total = 0;
      for (BadItem item : cart.getItems()) {
        total += item.getPrice() * item.getQuantity();
      }
      return total;
    }
  }

  // Good: Item이 자신의 금액 계산을 책임진다
  //   - 중복 계산식이 Item.totalPrice()로 이동한다
  //   - 합계 계산은 calculateTotal()로 모인다
  //   - 중복 제거 과정에서 새로운 개념이 드러난다
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
