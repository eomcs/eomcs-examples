package com.eomcs.cleancode.ch02.exam10;

public class BadAndGood3 {

  static class Order {
    double price;
    double discountRate;
  }

  // Bad
  // - magic, boom, doTheThing → 의미 없는 이름으로 코드를 직접 분석해야만 파악할 수 있다.
  // - 이름만 보고 무슨 작업인지 전혀 예측할 수 없다.
  static class BadOrderService {
    double magic(Order order) {
      return order.price * (1 - order.discountRate);
    }
    void boom(String userId) {
      System.out.println("알림 발송: " + userId);
    }
    void doTheThing(Order order) {
      System.out.println("주문 처리: " + order.price);
    }
  }

  // Good
  // - calculateDiscount, sendNotification, processOrder → 실제 수행하는 작업을 정확히 표현한다.
  // - 이름만 읽어도 각 메서드의 역할을 즉시 이해할 수 있다.
  static class GoodOrderService {
    double calculateDiscount(Order order) {
      return order.price * (1 - order.discountRate);
    }
    void sendNotification(String userId) {
      System.out.println("알림 발송: " + userId);
    }
    void processOrder(Order order) {
      System.out.println("주문 처리: " + order.price);
    }
  }
}
