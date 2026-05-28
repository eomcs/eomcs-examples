package com.eomcs.cleancode.ch12.exam03;

// 예제 3: 클래스와 메서드를 최소화하라 - PricePolicy vs Item
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

  // Bad: 구현이 하나뿐인데 인터페이스와 팩토리가 생겼다
  //   - 실제 변화 가능성보다 구조가 앞서 있다
  //   - 단순한 계산을 이해하기 위해 파일을 여러 개 봐야 한다
  interface BadPricePolicy {
    int calculate(BadItem item);
  }

  static class DefaultPricePolicy implements BadPricePolicy {

    @Override
    public int calculate(BadItem item) {
      return item.getPrice() * item.getQuantity();
    }
  }

  static class PricePolicyFactory {

    BadPricePolicy create() {
      return new DefaultPricePolicy();
    }
  }

  // Good: 필요한 구조만 남긴다
  //   - 불필요한 인터페이스, 팩토리, 계층을 만들지 않는다
  //   - 단순한 문제는 단순하게 해결한다
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
}
