package com.eomcs.cleancode.ch03.exam11;

import java.util.List;

public class BadAndGood4 {

  static class Item {
    private boolean invalid;
    Item(boolean invalid) { this.invalid = invalid; }
    boolean isInvalid() { return invalid; }
  }

  static class Order {
    private List<Item> items;
    Order(List<Item> items) { this.items = items; }
    List<Item> getItems() { return items; }
  }

  // Bad: 라벨 break(goto 스타일)를 사용한다.
  // - outer: 라벨이 붙은 break는 goto와 유사한 흐름 제어다.
  // - 중첩 루프가 깊어질수록 라벨 break를 추적하기 어렵다.
  // - 코드를 읽는 사람이 라벨을 찾아 시선을 이동해야 한다.
  // - Java에 goto 키워드는 없지만 라벨 break는 동일한 혼란을 만든다.
  static class BadOrderChecker {
    void check(List<Order> orders) {
      outer:
      for (Order order : orders) {
        for (Item item : order.getItems()) {
          if (item.isInvalid()) {
            System.out.println("유효하지 않은 항목 발견");
            break outer; // goto 스타일: 흐름 추적 어려움
          }
        }
      }
    }
  }

  // Good: 라벨 break 대신 함수로 분리한다.
  // - 중첩 루프를 별도 함수로 추출하면 라벨 없이도 return으로 즉시 탈출할 수 있다.
  // - 각 함수가 한 가지 역할만 담당하여 흐름이 명확하다.
  // - 함수 이름이 의도를 드러내므로 가독성이 높아진다.
  static class GoodOrderChecker {
    void check(List<Order> orders) {
      if (hasInvalidItem(orders)) {
        System.out.println("유효하지 않은 항목 발견");
      }
    }

    private boolean hasInvalidItem(List<Order> orders) {
      for (Order order : orders) {
        if (hasInvalidItem(order)) {
          return true;
        }
      }
      return false;
    }

    private boolean hasInvalidItem(Order order) {
      for (Item item : order.getItems()) {
        if (item.isInvalid()) {
          return true;
        }
      }
      return false;
    }
  }
}
