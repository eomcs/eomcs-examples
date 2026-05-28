package com.eomcs.cleancode.ch08.exam01;

// 예제 3: 외부 코드 사용하기 - 외부 API를 어댑터로 감싸라
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Order {
    private Long id;
    private int totalPrice;
    private String customerId;
    private boolean paid;

    Order(Long id, int totalPrice, String customerId) {
      this.id = id;
      this.totalPrice = totalPrice;
      this.customerId = customerId;
    }

    Long getId() { return id; }
    int getTotalPrice() { return totalPrice; }
    String getCustomerId() { return customerId; }
    void markPaid() { this.paid = true; }
    boolean isPaid() { return paid; }
  }

  // 외부 결제 라이브러리 (제어할 수 없는 서드파티 코드)
  static class ExternalPaymentRequest {
    private final int amount;
    private final String currency;
    private final String customerId;

    ExternalPaymentRequest(int amount, String currency, String customerId) {
      this.amount = amount;
      this.currency = currency;
      this.customerId = customerId;
    }

    int getAmount() { return amount; }
    String getCurrency() { return currency; }
    String getCustomerId() { return customerId; }
  }

  static class ExternalPaymentResponse {
    private final boolean success;
    private final String errorMessage;

    ExternalPaymentResponse(boolean success, String errorMessage) {
      this.success = success;
      this.errorMessage = errorMessage;
    }

    boolean isSuccess() { return success; }
    String getErrorMessage() { return errorMessage; }
  }

  interface ExternalPaymentClient {
    ExternalPaymentResponse execute(ExternalPaymentRequest request);
  }

  // 외부 결제 클라이언트 구현체 (서드파티 코드 시뮬레이션)
  static class StubExternalPaymentClient implements ExternalPaymentClient {
    @Override
    public ExternalPaymentResponse execute(ExternalPaymentRequest request) {
      System.out.printf("외부 API 호출: amount=%d, currency=%s, customerId=%s%n",
          request.getAmount(), request.getCurrency(), request.getCustomerId());
      return new ExternalPaymentResponse(true, null);
    }
  }

  static class BadPaymentException extends RuntimeException {
    BadPaymentException(String message) { super(message); }
  }

  // Bad: 서비스 코드가 외부 API의 요청/응답 구조를 직접 안다.
  // - 외부 API 변경 시 비즈니스 코드가 영향을 받는다.
  // - 결제 도메인 의도보다 API 사용법이 더 많이 드러난다.
  // - 테스트할 때 외부 라이브러리 객체를 직접 다뤄야 한다.
  static class BadOrderService {
    private final ExternalPaymentClient paymentClient;
    BadOrderService(ExternalPaymentClient client) { this.paymentClient = client; }

    public void pay(Order order) {
      ExternalPaymentRequest request = new ExternalPaymentRequest(
          order.getTotalPrice(), "KRW", order.getCustomerId()
      );

      ExternalPaymentResponse response = paymentClient.execute(request); // 외부 API 직접 호출

      if (!response.isSuccess()) {
        throw new BadPaymentException(response.getErrorMessage());
      }
    }
  }

  static class BadOrderClient {
    void run(BadOrderService orderService, Order order) {
      orderService.pay(order);
    }
  }

  // -----------------------------------------------------------------------

  // Good: 우리 애플리케이션용 인터페이스를 정의한다.
  // - 비즈니스 코드는 이 인터페이스만 안다.
  interface PaymentGateway {
    void pay(Order order);
  }

  static class PaymentFailedException extends RuntimeException {
    PaymentFailedException(String message) { super(message); }
  }

  // Good: 외부 API를 감싸는 어댑터.
  // - 외부 API 사용법이 어댑터 안에 갇힌다.
  // - 외부 라이브러리가 바뀌어도 변경 지점이 이 클래스에 한정된다.
  static class ExternalPaymentGateway implements PaymentGateway {
    private final ExternalPaymentClient paymentClient;
    ExternalPaymentGateway(ExternalPaymentClient client) { this.paymentClient = client; }

    @Override
    public void pay(Order order) {
      ExternalPaymentRequest request = new ExternalPaymentRequest(
          order.getTotalPrice(), "KRW", order.getCustomerId()
      );

      ExternalPaymentResponse response = paymentClient.execute(request);

      if (!response.isSuccess()) {
        throw new PaymentFailedException(response.getErrorMessage());
      }
    }
  }

  // Good: 서비스 코드는 우리 쪽 인터페이스(PaymentGateway)만 안다.
  // - 외부 API 변경이 비즈니스 코드에 영향을 주지 않는다.
  // - 테스트할 때 가짜 PaymentGateway를 쉽게 넣을 수 있다.
  static class GoodOrderService {
    private final PaymentGateway paymentGateway;
    GoodOrderService(PaymentGateway gateway) { this.paymentGateway = gateway; }

    public void pay(Order order) {
      paymentGateway.pay(order);
      order.markPaid();
    }
  }

  static class GoodOrderClient {
    void run(GoodOrderService orderService, Order order) {
      orderService.pay(order);
    }
  }
}
