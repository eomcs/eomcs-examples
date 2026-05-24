package com.eomcs.cleancode.ch03.exam10;

import java.util.List;

public class BadAndGood3 {

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

  // Bad
  // - 아이템 합계를 구하는 for 루프가 calculateRegularPrice()와 calculateDiscountedPrice() 양쪽에 그대로 반복된다.
  // - 가격 계산 방식이 바뀌면 두 곳을 모두 수정해야 한다.
  // - 중복이 작아 보여도 시간이 지날수록 변형된 중복이 계속 늘어난다.
  static class BadPriceCalculator {
    int calculateRegularPrice(Order order) {
      int total = 0;
      for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity(); // 중복
      }
      return total;
    }

    int calculateDiscountedPrice(Order order) {
      int total = 0;
      for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity(); // 중복
      }
      return (int) (total * 0.9);
    }
  }

  // Good: 공통 계산 로직을 calculateTotalPrice()로 추출한다.
  // - 기본 가격 계산은 calculateTotalPrice() 한 곳에서 관리한다.
  // - 할인 정책은 applyDiscount()로 분리하여 독립적으로 변경할 수 있다.
  // - 각 함수가 하나의 역할만 담당하여 의도가 명확하다.
  static class GoodPriceCalculator {
    int calculateRegularPrice(Order order) {
      return calculateTotalPrice(order);
    }

    int calculateDiscountedPrice(Order order) {
      return applyDiscount(calculateTotalPrice(order));
    }

    private int calculateTotalPrice(Order order) {
      int total = 0;
      for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
      }
      return total;
    }

    private int applyDiscount(int totalPrice) {
      return (int) (totalPrice * 0.9);
    }
  }
}
