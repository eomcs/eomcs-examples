package com.eomcs.cleancode.ch12.exam02;

import java.util.ArrayList;
import java.util.List;

// 예제 3: 리팩토링과의 관계 — 테스트가 보호하는 상태에서 구조를 개선한다
public class BadAndGood3 {

  private BadAndGood3() {}

  // 리팩토링 전: Item은 가격과 수량을 노출하고, OrderService가 직접 계산한다
  static class Before {

    static class Item {

      private final int price;
      private final int quantity;

      Item(int price, int quantity) {
        this.price = price;
        this.quantity = quantity;
      }

      int getPrice() { return price; }
      int getQuantity() { return quantity; }
    }

    static class Order {

      private final List<Item> items = new ArrayList<>();

      void addItem(Item item) {
        items.add(item);
      }

      List<Item> getItems() { return items; }
    }

    // 초기 구현: for 루프로 직접 price * quantity 계산
    static class OrderService {

      public int totalPrice(Order order) {
        int total = 0;

        for (Item item : order.getItems()) {
          total += item.getPrice() * item.getQuantity();
        }

        return total;
      }
    }
  }

  // 리팩토링 후: Item이 자신의 금액 계산을 책임진다
  //   - 테스트가 깨지지 않으면 리팩토링 성공
  //   - 구조는 바뀌었지만 동작은 동일
  //   - 이것이 Emergent Design의 핵심
  static class After {

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

      void addItem(Item item) {
        items.add(item);
      }

      List<Item> getItems() { return items; }
    }

    // 리팩토링 후: stream + 메서드 참조로 의도가 드러난다
    static class OrderService {

      public int totalPrice(Order order) {
        return order.getItems().stream()
            .mapToInt(Item::totalPrice)
            .sum();
      }
    }
  }
}
