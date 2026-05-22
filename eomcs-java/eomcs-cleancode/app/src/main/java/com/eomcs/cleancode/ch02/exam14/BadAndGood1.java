package com.eomcs.cleancode.ch02.exam14;

public class BadAndGood1 {

  static class Data {}

  static class Order {
    String id;
    double totalPrice;
  }

  // Bad
  // - DataProcessor → 무엇을 처리하는 클래스인지 알 수 없다.
  // - Data, process → 너무 일반적이어서 비즈니스 의미가 완전히 사라진다.
  static class BadDataProcessor {
    void process(Data data) {
      System.out.println("데이터 처리");
    }
  }

  // Good
  // - OrderProcessor → 도메인 개념(Order)이 이름에 직접 드러난다.
  // - processOrder → 업무 의미가 명확하다.
  static class GoodOrderProcessor {
    void processOrder(Order order) {
      System.out.println("주문 처리: " + order.id);
    }
  }
}
