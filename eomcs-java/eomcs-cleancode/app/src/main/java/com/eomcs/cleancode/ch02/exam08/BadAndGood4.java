package com.eomcs.cleancode.ch02.exam08;

public class BadAndGood4 {

  static class User {
    int id;
    String name;
    String email;
  }

  static class Order {
    double price;
    int quantity;
    String coupon;
  }

  // Bad
  // - SaveUser, CalculatePrice, ProcessOrder → 클래스가 아니라 동작처럼 읽힌다.
  // - 클래스는 "무엇인가(명사)"여야 하는데 동사로 시작해 역할이 혼동된다.
  static class SaveUser {
    void execute(User user) {
      System.out.println("사용자 저장: " + user.name);
    }
  }

  static class CalculatePrice {
    double execute(Order order) {
      return order.price * order.quantity;
    }
  }

  static class ProcessOrder {
    void execute(Order order) {
      System.out.println("주문 처리: " + order.price);
    }
  }

  // Good
  // - UserSaver, PriceCalculator, OrderProcessor → 명사형으로 변경.
  // - 객체의 역할이 강조되고, 클래스임을 즉시 알 수 있다.
  static class UserSaver {
    void save(User user) {
      System.out.println("사용자 저장: " + user.name);
    }
  }

  static class PriceCalculator {
    double calculate(Order order) {
      return order.price * order.quantity;
    }
  }

  static class OrderProcessor {
    void process(Order order) {
      System.out.println("주문 처리: " + order.price);
    }
  }
}
