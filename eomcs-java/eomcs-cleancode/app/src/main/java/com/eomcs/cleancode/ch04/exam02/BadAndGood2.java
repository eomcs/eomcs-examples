package com.eomcs.cleancode.ch04.exam02;

import java.util.List;

public class BadAndGood2 {

  static class Item {
    int price;
    int quantity;
    Item(int price, int quantity) { this.price = price; this.quantity = quantity; }
  }

  // Bad
  // - total이라는 이름만으로는 "총 금액"인지 "총 수량"인지 알 수 없다.
  // - 루프 내부의 계산 로직(price * quantity)이 직접 노출되어 있다.
  // - "// calculate total price" 주석이 코드 대신 의도를 설명하고 있다.
  static class BadOrderSummary {
    void printSummary(List<Item> items) {
      // calculate total price
      int total = 0;
      for (Item item : items) {
        total += item.price * item.quantity;
      }
      System.out.println("합계: " + total);
    }
  }

  // Good: 함수 이름이 주석 역할을 대신한다.
  // - calculateTotalPrice()라는 이름만으로 무엇을 계산하는지 즉시 이해할 수 있다.
  // - totalPrice라는 변수 이름이 의미를 명확히 전달한다.
  // - 코드가 자연어처럼 읽힌다.
  static class GoodOrderSummary {
    void printSummary(List<Item> items) {
      int totalPrice = calculateTotalPrice(items);
      System.out.println("합계: " + totalPrice);
    }

    private int calculateTotalPrice(List<Item> items) {
      int total = 0;
      for (Item item : items) {
        total += item.price * item.quantity;
      }
      return total;
    }
  }
}
