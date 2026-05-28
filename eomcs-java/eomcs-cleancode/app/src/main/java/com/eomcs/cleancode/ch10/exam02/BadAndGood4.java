package com.eomcs.cleancode.ch10.exam02;

// 예제 4: 응집도를 유지하면 작은 클래스가 많아진다 - OrderProcessor
public class BadAndGood4 {

  private BadAndGood4() {}

  static class Order {
    private final int price;

    Order(int price) {
      this.price = price;
    }

    int totalPrice() { return price; }
  }

  // Bad: 할인·배송비·최종가격·영수증 발송이 한 클래스에 혼재 - 응집도 낮음
  static class BadOrderProcessor {

    private final Order order;
    private final int discountRate;
    private final int shippingFee;
    private final String receiverEmail;

    BadOrderProcessor(Order order, int discountRate, int shippingFee, String receiverEmail) {
      this.order = order;
      this.discountRate = discountRate;
      this.shippingFee = shippingFee;
      this.receiverEmail = receiverEmail;
    }

    int calculateDiscount() {
      return order.totalPrice() * discountRate / 100;
    }

    int calculateShippingFee() {
      if (order.totalPrice() >= 50_000) {
        return 0;
      }
      return shippingFee;
    }

    int finalPrice() {
      return order.totalPrice()
          - calculateDiscount()
          + calculateShippingFee();
    }

    void sendReceipt() {
      System.out.println("send receipt to " + receiverEmail);
    }
  }

  // Good: 할인 정책 책임만 가짐
  static class DiscountPolicy {

    private final int discountRate;

    DiscountPolicy(int discountRate) {
      this.discountRate = discountRate;
    }

    int discountFor(Order order) {
      return order.totalPrice() * discountRate / 100;
    }
  }

  // Good: 배송비 정책 책임만 가짐
  static class ShippingPolicy {

    private final int defaultShippingFee;

    ShippingPolicy(int defaultShippingFee) {
      this.defaultShippingFee = defaultShippingFee;
    }

    int shippingFeeFor(Order order) {
      if (order.totalPrice() >= 50_000) {
        return 0;
      }
      return defaultShippingFee;
    }
  }

  // Good: 영수증 발송 책임만 가짐
  static class ReceiptSender {

    private final String receiverEmail;

    ReceiptSender(String receiverEmail) {
      this.receiverEmail = receiverEmail;
    }

    void send(Order order, int finalPrice) {
      System.out.println("send receipt to " + receiverEmail);
    }
  }

  // Good: 최종 가격 계산 조율만 담당 - 각 정책 클래스에 위임
  static class OrderPriceCalculator {

    private final Order order;
    private final DiscountPolicy discountPolicy;
    private final ShippingPolicy shippingPolicy;

    OrderPriceCalculator(Order order, DiscountPolicy discountPolicy, ShippingPolicy shippingPolicy) {
      this.order = order;
      this.discountPolicy = discountPolicy;
      this.shippingPolicy = shippingPolicy;
    }

    int finalPrice() {
      return order.totalPrice()
          - discountPolicy.discountFor(order)
          + shippingPolicy.shippingFeeFor(order);
    }
  }
}
