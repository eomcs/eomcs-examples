package com.eomcs.cleancode.ch07.exam05;

// 예제 3: 호출자 기준으로 예외를 그룹화하라 - processPayment
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Order {
    private Long id;
    Order(Long id) { this.id = id; }
    Long getId() { return id; }
  }

  // 내부 기술 예외들
  static class NetworkException extends Exception {
    NetworkException(String msg) { super(msg); }
  }

  static class BankException extends Exception {
    BankException(String msg) { super(msg); }
  }

  static class TimeoutException extends Exception {
    TimeoutException(String msg) { super(msg); }
  }

  interface PaymentGateway {
    void process(Order order) throws NetworkException, BankException, TimeoutException;
  }

  // Bad: 내부 기술 예외 3개를 그대로 노출한다.
  // - 호출자가 구현 세부사항(네트워크, 은행, 타임아웃)을 모두 알아야 한다.
  // - 같은 처리(retry)를 여러 catch 블록에서 반복한다.
  // - 예외가 추가될 때마다 호출자 코드도 변경해야 한다.
  static class BadPaymentService {
    private PaymentGateway paymentGateway;
    BadPaymentService(PaymentGateway gateway) { this.paymentGateway = gateway; }

    public void processPayment(Order order)
        throws NetworkException, BankException, TimeoutException {
      paymentGateway.process(order);
    }
  }

  static class BadPaymentClient {
    void run(BadPaymentService paymentService, Order order) {
      try {
        paymentService.processPayment(order);
      } catch (NetworkException e) {
        retry(); // 동일한 처리가 반복된다
      } catch (BankException e) {
        cancelOrder(order);
      } catch (TimeoutException e) {
        retry(); // 동일한 처리가 반복된다
      }
    }

    private void retry() { System.out.println("재시도"); }
    private void cancelOrder(Order order) { System.out.println("주문 취소: " + order.getId()); }
  }

  // -----------------------------------------------------------------------

  // Good: 호출자의 처리 방식 기준으로 예외를 그룹화한다.
  // - 재시도 가능한 오류 → RetryablePaymentException
  // - 결제 자체가 실패한 오류 → PaymentFailedException
  static class RetryablePaymentException extends RuntimeException {
    RetryablePaymentException(Throwable cause) { super("재시도 가능한 결제 오류", cause); }
  }

  static class PaymentFailedException extends RuntimeException {
    PaymentFailedException(Throwable cause) { super("결제 실패", cause); }
  }

  // Good: 호출자가 원하는 처리 기준으로 예외를 분류한다.
  // - NetworkException과 TimeoutException은 "재시도" 대상 → RetryablePaymentException
  // - BankException은 "취소" 대상 → PaymentFailedException
  // - 동일한 처리 로직이 하나의 catch로 묶인다.
  static class GoodPaymentService {
    private PaymentGateway paymentGateway;
    GoodPaymentService(PaymentGateway gateway) { this.paymentGateway = gateway; }

    public void processPayment(Order order) {
      try {
        paymentGateway.process(order);
      } catch (NetworkException | TimeoutException e) {
        throw new RetryablePaymentException(e);
      } catch (BankException e) {
        throw new PaymentFailedException(e);
      }
    }
  }

  static class GoodPaymentClient {
    void run(GoodPaymentService paymentService, Order order) {
      try {
        paymentService.processPayment(order);
      } catch (RetryablePaymentException e) {
        retry(); // 재시도 처리가 하나로 묶인다
      } catch (PaymentFailedException e) {
        cancelOrder(order);
      }
    }

    private void retry() { System.out.println("재시도"); }
    private void cancelOrder(Order order) { System.out.println("주문 취소: " + order.getId()); }
  }
}
