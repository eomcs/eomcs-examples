package com.eomcs.cleancode.ch12.exam01;

import java.util.List;

// 예제 3: 중복을 제거하며 숨은 개념을 찾아라 - PriceCalculator / Item
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

    private final List<BadItem> items;

    BadOrder(List<BadItem> items) {
      this.items = items;
    }

    List<BadItem> getItems() { return items; }
  }

  static class BadCart {

    private final List<BadItem> items;

    BadCart(List<BadItem> items) {
      this.items = items;
    }

    List<BadItem> getItems() { return items; }
  }

  // Bad: item.getPrice() * item.getQuantity() 중복
  //   - 주문과 장바구니 계산 방식이 같은데 따로 구현되어 있다
  //   - 계산 규칙이 바뀌면 여러 곳을 수정해야 한다
  static class BadPriceCalculator {

    public int calculateOrderPrice(BadOrder order) {
      return order.getItems().stream()
          .mapToInt(item -> item.getPrice() * item.getQuantity())
          .sum();
    }

    public int calculateCartPrice(BadCart cart) {
      return cart.getItems().stream()
          .mapToInt(item -> item.getPrice() * item.getQuantity())
          .sum();
    }
  }

  // Good: Item이 자신의 금액 계산을 책임진다
  //   - 중복을 제거하면서 Item.totalPrice()라는 개념이 드러난다
  //   - 설계가 더 객체지향적으로 이동한다
  //   - 이것이 "창발적 설계"의 핵심 흐름이다
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

    private final List<Item> items;

    Order(List<Item> items) {
      this.items = items;
    }

    List<Item> getItems() { return items; }
  }

  static class Cart {

    private final List<Item> items;

    Cart(List<Item> items) {
      this.items = items;
    }

    List<Item> getItems() { return items; }
  }

  // Good: 중복이 사라지고 각 메서드의 의도가 드러난다
  static class PriceCalculator {

    public int calculateOrderPrice(Order order) {
      return calculateItemsPrice(order.getItems());
    }

    public int calculateCartPrice(Cart cart) {
      return calculateItemsPrice(cart.getItems());
    }

    private int calculateItemsPrice(List<Item> items) {
      return items.stream()
          .mapToInt(Item::totalPrice)
          .sum();
    }
  }
}
