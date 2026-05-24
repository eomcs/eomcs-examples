package com.eomcs.cleancode.ch04.exam04;

import java.util.List;

public class BadAndGood18 {

  static class Item {
    private int price;
    Item(int price) { this.price = price; }
    int price() { return price; }
  }

  // Bad: 비공개 코드에서 Javadocs (Javadocs in Nonpublic Code)
  // - private 메서드는 외부에서 볼 수 없으므로 Javadoc이 쓸모없다.
  // - 내부 코드의 무분별한 Javadoc은 오히려 잡음이 된다.
  // - Javadoc은 public API 사용자를 위한 문서다.
  static class BadOrderService {
    /**
     * Calculates total price.
     *
     * @param items items
     * @return total price
     */
    private int calculateTotalPrice(List<Item> items) {
      return items.stream().mapToInt(Item::price).sum();
    }

    public void printTotal(List<Item> items) {
      System.out.println("합계: " + calculateTotalPrice(items));
    }
  }

  // Good: private 메서드에는 Javadoc을 달지 않는다.
  // - 메서드 이름 calculateTotalPrice()가 역할을 충분히 설명한다.
  // - Javadoc이 필요하다면 해당 메서드를 public API로 승격하는 것을 고려하라.
  static class GoodOrderService {
    private int calculateTotalPrice(List<Item> items) {
      return items.stream().mapToInt(Item::price).sum();
    }

    public void printTotal(List<Item> items) {
      System.out.println("합계: " + calculateTotalPrice(items));
    }
  }
}
