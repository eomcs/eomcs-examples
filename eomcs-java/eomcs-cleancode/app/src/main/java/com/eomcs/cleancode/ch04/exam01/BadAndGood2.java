package com.eomcs.cleancode.ch04.exam01;

import java.util.List;

public class BadAndGood2 {

  static class Item {
    int price;
    int quantity;
    Item(int price, int quantity) { this.price = price; this.quantity = quantity; }
  }

  // Bad
  // - x, y는 아무 의미도 없는 이름이다. 주석이 없으면 무엇을 계산하는지 알 수 없다.
  // - 주석이 코드의 의미를 대신하고 있다 → 주석이 없어지면 코드도 이해 불가가 된다.
  // - 코드와 주석을 함께 읽어야 하므로 읽는 비용이 두 배가 된다.
  static class BadPriceCalculator {
    int calculate(List<Item> items) {
      // initialize variables
      int x = 0;
      int y = 0;

      // calculate total
      for (Item item : items) {
        x += item.price * item.quantity;
      }

      // apply tax
      y = (int) (x * 1.1);

      return y;
    }
  }

  // Good: 이름과 함수 분리로 주석을 제거한다.
  // - totalPrice, totalPriceWithTax라는 이름이 변수의 역할을 바로 설명한다.
  // - calculateTotalPrice()와 applyTax()라는 함수 이름이 로직을 설명한다.
  // - 주석이 전혀 없어도 코드를 이해할 수 있다.
  static class GoodPriceCalculator {
    int calculate(List<Item> items) {
      int totalPrice = calculateTotalPrice(items);
      int totalPriceWithTax = applyTax(totalPrice);
      return totalPriceWithTax;
    }

    private int calculateTotalPrice(List<Item> items) {
      int total = 0;
      for (Item item : items) {
        total += item.price * item.quantity;
      }
      return total;
    }

    private int applyTax(int price) {
      return (int) (price * 1.1);
    }
  }
}
