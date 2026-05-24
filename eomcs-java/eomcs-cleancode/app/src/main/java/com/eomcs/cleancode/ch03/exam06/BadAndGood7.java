package com.eomcs.cleancode.ch03.exam06;

import java.util.Arrays;
import java.util.List;

public class BadAndGood7 {

  static class Item {
    String name;
    Item(String name) { this.name = name; }
  }

  // Bad
  // - 고정된 인자 3개 → 아이템 수가 달라지면 메서드 시그니처를 변경해야 한다.
  // - 아이템이 2개이거나 4개인 경우를 처리할 수 없다.
  static class BadCart {
    void addItems(Item item1, Item item2, Item item3) {
      System.out.println("추가: " + item1.name + ", " + item2.name + ", " + item3.name);
    }
  }

  // Good: List 또는 가변 인자(varargs)를 사용한다.
  // - 아이템 수가 달라져도 메서드를 변경할 필요가 없다.
  static class GoodCart {
    // List 방식
    void addItems(List<Item> items) {
      items.forEach(item -> System.out.println("추가: " + item.name));
    }

    // 가변 인자(varargs) 방식
    void addItemsVarargs(Item... items) {
      for (Item item : items) {
        System.out.println("추가: " + item.name);
      }
    }
  }

  static class GoodUsage {
    void demo() {
      GoodCart cart = new GoodCart();
      cart.addItems(Arrays.asList(new Item("사과"), new Item("바나나")));
      cart.addItemsVarargs(new Item("사과"), new Item("바나나"), new Item("포도"));
    }
  }
}
