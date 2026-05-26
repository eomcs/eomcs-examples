package com.eomcs.cleancode.ch07.exam04;

// 예제 2: 예외에 의미를 제공하라 - pay
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Logger {
    void error(String message, Throwable e) {
      System.out.println("[ERROR] " + message + " | cause: " + e.getClass().getSimpleName());
    }
  }

  static class Order {
    private Long id;
    private int totalPrice;
    Order(Long id, int totalPrice) { this.id = id; this.totalPrice = totalPrice; }
    Long getId() { return id; }
    int getTotalPrice() { return totalPrice; }
    void markPaid() { System.out.println("주문 결제 완료: orderId=" + id); }
  }

  interface PaymentGateway {
    boolean approve(int amount);
  }

  // Bad: 실패 원인과 대상 정보가 없는 모호한 예외를 던진다.
  // - 결제 실패 원인이 불명확하다.
  // - 어떤 주문에서 실패했는지 알 수 없다.
  // - 금액, 주문 번호 같은 핵심 정보가 없다.
  static class BadOrderService {
    private PaymentGateway paymentGateway;
    BadOrderService(PaymentGateway gateway) { this.paymentGateway = gateway; }

    @SuppressWarnings("java:S112") // 의도적인 Bad 예제 — 모호한 RuntimeException이 핵심 문제점
    public void pay(Order order) {
      if (!paymentGateway.approve(order.getTotalPrice())) {
        throw new RuntimeException("Payment failed");
      }

      order.markPaid();
    }
  }

  static class BadOrderClient {
    void run(BadOrderService orderService, Order order, Logger logger) {
      try {
        orderService.pay(order);
      } catch (RuntimeException e) {
        logger.error(e.getMessage(), e); // 로그: "Payment failed" — 추적 불가
      }
    }
  }

  // -----------------------------------------------------------------------

  // Good: 실패한 주문 번호와 금액을 예외 메시지에 담는다.
  static class PaymentFailedException extends RuntimeException {
    PaymentFailedException(String message) { super(message); }
  }

  // Good: orderId와 amount를 메시지에 포함한다.
  // - 어떤 주문의 결제가 실패했는지 알 수 있다.
  // - 실패한 금액이 함께 기록된다.
  // - 로그만으로 장애 분석이 쉬워진다.
  static class GoodOrderService {
    private PaymentGateway paymentGateway;
    GoodOrderService(PaymentGateway gateway) { this.paymentGateway = gateway; }

    public void pay(Order order) {
      boolean approved = paymentGateway.approve(order.getTotalPrice());

      if (!approved) {
        throw new PaymentFailedException(
            "결제 승인 실패. orderId=" + order.getId()
                + ", amount=" + order.getTotalPrice()
        );
      }

      order.markPaid();
    }
  }

  static class GoodOrderClient {
    void run(GoodOrderService orderService, Order order, Logger logger) {
      try {
        orderService.pay(order);
      } catch (PaymentFailedException e) {
        logger.error(e.getMessage(), e); // 로그: "결제 승인 실패. orderId=1, amount=50000"
      }
    }
  }
}
