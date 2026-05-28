package com.eomcs.cleancode.ch08.exam05;

// 예제 2: 아직 존재하지 않는 코드를 사용하기 - OrderService.pay()
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Order {
    private String id;
    private int totalPrice;
    private boolean paid;

    Order(String id, int totalPrice) { this.id = id; this.totalPrice = totalPrice; }

    String getId() { return id; }
    int getTotalPrice() { return totalPrice; }
    void markPaid() { this.paid = true; }
    boolean isPaid() { return paid; }
  }

  // Bad: 결제 API 스펙이 확정될 때까지 구현을 미룬다.
  // - 결제 API가 확정될 때까지 주문 서비스 개발이 지연된다.
  // - 주문 서비스 테스트를 작성하기 어렵다.
  // - 외부 API 이름과 구조가 비즈니스 코드에 직접 들어올 가능성이 높다.
  static class BadOrderService {
    public void pay(Order order) {
      // TODO: 결제팀 API 스펙 확정 후 구현
      // ExternalPaymentClient? PaymentApi? 아직 모름
    }
  }

  static class BadOrderClient {
    void run(BadOrderService orderService, Order order) {
      orderService.pay(order);
      // 실제로 아무 일도 일어나지 않는다
    }
  }

  // -----------------------------------------------------------------------

  // Good: 주문 서비스가 원하는 결제 경계를 먼저 정의한다.
  // - 결제 API가 없어도 주문 서비스를 개발하고 테스트할 수 있다.
  // - pay(orderId, amount)라는 우리 쪽 요구사항이 먼저 드러난다.
  // - 실제 결제 API가 나오면 어댑터에서만 변환하면 된다.
  interface PaymentGateway {
    void pay(String orderId, int amount);
  }

  // Good: PaymentGateway 인터페이스를 주입받아 사용한다.
  static class GoodOrderService {
    private final PaymentGateway paymentGateway;
    GoodOrderService(PaymentGateway gateway) { this.paymentGateway = gateway; }

    public void pay(Order order) {
      paymentGateway.pay(order.getId(), order.getTotalPrice());
      order.markPaid();
    }
  }

  // Good: 테스트용 가짜 구현 - 실제 결제 API 없이도 동작을 검증할 수 있다.
  static class FakePaymentGateway implements PaymentGateway {
    private String paidOrderId;
    private int paidAmount;

    @Override
    public void pay(String orderId, int amount) {
      this.paidOrderId = orderId;
      this.paidAmount = amount;
    }

    String getPaidOrderId() { return paidOrderId; }
    int getPaidAmount() { return paidAmount; }
  }

  static class GoodOrderClient {
    void run(GoodOrderService orderService, Order order) {
      orderService.pay(order);
    }
  }
}
