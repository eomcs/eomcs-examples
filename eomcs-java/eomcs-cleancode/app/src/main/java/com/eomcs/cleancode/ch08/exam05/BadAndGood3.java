package com.eomcs.cleancode.ch08.exam05;

// 예제 3: 실제 API가 나온 뒤 Adapter로 연결한다
public class BadAndGood3 {

  private BadAndGood3() {}

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

  // 나중에 확정된 외부 결제 API (서드파티 코드 - 제어 불가)
  static class ExternalPaymentRequest {
    private String merchantOrderNo;
    private int paymentAmount;
    private String currency;

    void setMerchantOrderNo(String merchantOrderNo) { this.merchantOrderNo = merchantOrderNo; }
    void setPaymentAmount(int paymentAmount) { this.paymentAmount = paymentAmount; }
    void setCurrency(String currency) { this.currency = currency; }
    String getMerchantOrderNo() { return merchantOrderNo; }
    int getPaymentAmount() { return paymentAmount; }
    String getCurrency() { return currency; }
  }

  static class ExternalPaymentResponse {
    private final boolean success;
    ExternalPaymentResponse(boolean success) { this.success = success; }
    boolean isSuccess() { return success; }
  }

  static class ExternalPaymentClient {
    public ExternalPaymentResponse execute(ExternalPaymentRequest request) {
      System.out.printf("외부 결제 API 호출: orderId=%s, amount=%d, currency=%s%n",
          request.getMerchantOrderNo(), request.getPaymentAmount(), request.getCurrency());
      return new ExternalPaymentResponse(true);
    }
  }

  // 우리가 미리 정의한 인터페이스 (BadAndGood2에서 그대로 유지)
  interface PaymentGateway {
    void pay(String orderId, int amount);
  }

  static class PaymentFailedException extends RuntimeException {
    PaymentFailedException(String message) { super(message); }
  }

  // Good: 실제 API가 나온 뒤 Adapter를 만들어 연결한다.
  // - 외부 API의 복잡한 요청/응답 구조가 Adapter 안에 갇힌다.
  // - OrderService는 변경되지 않는다.
  // - 외부 API가 바뀌어도 수정 지점은 이 클래스로 제한된다.
  static class ExternalPaymentGateway implements PaymentGateway {
    private final ExternalPaymentClient client;
    ExternalPaymentGateway(ExternalPaymentClient client) { this.client = client; }

    @Override
    public void pay(String orderId, int amount) {
      ExternalPaymentRequest request = new ExternalPaymentRequest();
      request.setMerchantOrderNo(orderId);
      request.setPaymentAmount(amount);
      request.setCurrency("KRW");

      ExternalPaymentResponse response = client.execute(request);

      if (!response.isSuccess()) {
        throw new PaymentFailedException("결제 실패. orderId=" + orderId);
      }
    }
  }

  // Good: OrderService는 인터페이스(PaymentGateway)만 알고, 외부 API는 모른다.
  static class OrderService {
    private final PaymentGateway paymentGateway;
    OrderService(PaymentGateway gateway) { this.paymentGateway = gateway; }

    public void pay(Order order) {
      paymentGateway.pay(order.getId(), order.getTotalPrice());
      order.markPaid();
    }
  }

  static class GoodOrderClient {
    void run() {
      // 실제 API가 나오기 전: FakePaymentGateway로 테스트
      PaymentGateway fake = (orderId, amount) ->
          System.out.printf("가짜 결제: orderId=%s, amount=%d%n", orderId, amount);
      OrderService orderService = new OrderService(fake);
      orderService.pay(new Order("ORDER-001", 50_000));

      // 실제 API가 나온 후: Adapter를 끼워 넣는다 - OrderService 코드는 변경 없음
      PaymentGateway real = new ExternalPaymentGateway(new ExternalPaymentClient());
      OrderService realOrderService = new OrderService(real);
      realOrderService.pay(new Order("ORDER-002", 80_000));
    }
  }
}
