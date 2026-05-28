package com.eomcs.cleancode.ch12.exam03;

import java.util.ArrayList;
import java.util.List;

// 예제 4: 리팩토링의 순서 — 테스트 통과 → 중복 제거 → 의도 드러내기
public class BadAndGood4 {

  private BadAndGood4() {}

  // 1단계와 2단계에서 사용하는 Item: getPrice/getQuantity 노출
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

      void addItem(Item item) { items.add(item); }
      List<Item> getItems() { return items; }
    }

    // 1단계: 테스트가 통과하는 코드 — for 루프
    static class Step1OrderService {

      public int totalPrice(Order order) {
        int total = 0;
        for (Item item : order.getItems()) {
          total += item.getPrice() * item.getQuantity();
        }
        return total;
      }
    }

    // 2단계: 중복 또는 숨은 개념 발견 — 스트림 + 람다
    static class Step2OrderService {

      public int totalPrice(Order order) {
        return order.getItems().stream()
            .mapToInt(item -> item.getPrice() * item.getQuantity())
            .sum();
      }
    }
  }

  // 3단계: 의도를 드러내는 메서드 추출 — Item.totalPrice() + 메서드 참조
  //   - 테스트 통과 상태를 유지한다
  //   - 이름을 통해 의도를 드러낸다
  //   - 필요 없는 구조는 추가하지 않는다
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

      void addItem(Item item) { items.add(item); }
      List<Item> getItems() { return items; }
    }

    static class OrderService {

      public int totalPrice(Order order) {
        return order.getItems().stream()
            .mapToInt(Item::totalPrice)
            .sum();
      }
    }
  }
}
