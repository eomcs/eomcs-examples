package com.eomcs.cleancode.ch03.exam06;

public class BadAndGood3 {

  static class Order {
    String id;
    Order(String id) { this.id = id; }
  }

  // Bad
  // - boolean 플래그 → 호출부에서 true/false 의 의미를 기억해야 한다.
  // - 함수 내부에 if/else 분기 → 하나의 함수가 두 가지 일을 한다.
  static class BadOrderService {
    void processOrder(Order order, boolean isUrgent) {
      if (isUrgent) {
        System.out.println("긴급 처리: " + order.id);
      } else {
        System.out.println("일반 처리: " + order.id);
      }
    }
  }

  static class BadUsage {
    void demo(Order order) {
      BadOrderService service = new BadOrderService();
      service.processOrder(order, true);  // true가 무슨 의미인지 호출부에서 알 수 없다.
      service.processOrder(order, false);
    }
  }

  // Good
  // - 플래그 대신 함수를 분리한다.
  // - 함수 이름만으로 의도를 즉시 이해할 수 있다.
  static class GoodOrderService {
    void processUrgentOrder(Order order) {
      System.out.println("긴급 처리: " + order.id);
    }
    void processNormalOrder(Order order) {
      System.out.println("일반 처리: " + order.id);
    }
  }

  static class GoodUsage {
    void demo(Order order) {
      GoodOrderService service = new GoodOrderService();
      service.processUrgentOrder(order); // 의도가 명확하다.
      service.processNormalOrder(order);
    }
  }
}
