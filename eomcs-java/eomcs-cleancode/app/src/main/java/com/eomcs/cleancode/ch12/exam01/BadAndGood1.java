package com.eomcs.cleancode.ch12.exam01;

import java.util.List;

// 예제 1: 계산 로직을 역할별로 분리하라 - OrderService
public class BadAndGood1 {

  private BadAndGood1() {}

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

  static class Coupon {

    private final int discountRate;

    Coupon(int discountRate) {
      this.discountRate = discountRate;
    }

    int getDiscountRate() { return discountRate; }
  }

  static class Order {

    private final List<Item> items;
    private final Coupon coupon;

    Order(List<Item> items, Coupon coupon) {
      this.items = items;
      this.coupon = coupon;
    }

    Order(List<Item> items) {
      this(items, null);
    }

    List<Item> getItems() { return items; }
    Coupon getCoupon() { return coupon; }
  }

  // Bad: 계산 로직이 한 메서드에 몰려 있다
  //   - 상품 금액 계산과 쿠폰 할인 계산이 섞여 있다
  //   - 테스트는 가능하지만 설계 의도가 잘 드러나지 않는다
  static class BadOrderService {

    public int calculateTotalPrice(Order order) {
      int total = 0;

      for (Item item : order.getItems()) {
        total += item.getPrice() * item.getQuantity();
      }

      if (order.getCoupon() != null) {
        total -= total * order.getCoupon().getDiscountRate() / 100;
      }

      return total;
    }
  }

  // Good: 계산 단계가 이름으로 드러난다
  //   - 테스트 통과 상태를 유지하면서 리팩토링한다
  //   - 중복 가능성이 있는 계산 로직이 분리된다
  //   - 설계가 점진적으로 깨끗해진다
  static class OrderService {

    public int calculateTotalPrice(Order order) {
      int itemTotal = calculateItemTotal(order);
      return applyCouponDiscount(itemTotal, order.getCoupon());
    }

    private int calculateItemTotal(Order order) {
      return order.getItems().stream()
          .mapToInt(this::calculateItemPrice)
          .sum();
    }

    private int calculateItemPrice(Item item) {
      return item.getPrice() * item.getQuantity();
    }

    private int applyCouponDiscount(int total, Coupon coupon) {
      if (coupon == null) {
        return total;
      }
      return total - total * coupon.getDiscountRate() / 100;
    }
  }
}
