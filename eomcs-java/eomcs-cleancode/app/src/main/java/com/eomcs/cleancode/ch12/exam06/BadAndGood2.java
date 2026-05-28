package com.eomcs.cleancode.ch12.exam06;

// 예제 2: 의미 없는 메서드 분리를 제거하라 - PriceCalculator / Item
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

    // Good: 도메인 의미가 있을 때만 메서드로 뽑는다
    int totalPrice() {
      return price * quantity;
    }
  }

  // Bad: 너무 잘게 쪼갠 메서드
  //   - 메서드는 많지만 의미 있는 추상화가 아니다
  //   - getPrice(), getQuantity(), multiply()는 코드 자체보다 더 설명적이지 않다
  //   - 읽는 사람이 메서드를 계속 따라가야 한다
  static class BadPriceCalculator {

    public int calculate(Item item) {
      int price = getPrice(item);
      int quantity = getQuantity(item);
      return multiply(price, quantity);
    }

    private int getPrice(Item item) {
      return item.getPrice();
    }

    private int getQuantity(Item item) {
      return item.getQuantity();
    }

    private int multiply(int price, int quantity) {
      return price * quantity;
    }
  }

  // Good: 단순한 코드는 단순하게 둔다
  //   - 의미 없는 메서드 분리를 제거한다
  static class PriceCalculator {

    public int calculate(Item item) {
      return item.getPrice() * item.getQuantity();
    }
  }

  // Good: 더 도메인 중심으로 — Item이 자신의 금액을 안다
  //   - totalPrice()처럼 도메인 의미가 있을 때만 메서드로 뽑는다
  static class DomainPriceCalculator {

    public int calculate(Item item) {
      return item.totalPrice();
    }
  }
}
