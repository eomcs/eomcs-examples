package com.eomcs.cleancode.ch07.exam03;

// 예제 2: 포괄적인 throws Exception 남용 - processOrder
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Order {
    private boolean paid;
    Order(boolean paid) { this.paid = paid; }
    boolean isPaid() { return paid; }
  }

  // Bad: 포괄적인 throws Exception을 사용한다.
  // - 어떤 오류인지 타입으로 구분할 수 없다.
  // - 호출자가 의미 있는 처리를 할 수 없다.
  // - Checked Exception으로 모든 호출자에게 처리가 강제된다.
  static class BadOrderProcessor {
    public void processOrder(Order order) throws Exception {
      if (order == null) {
        throw new Exception("잘못된 주문");
      }

      if (!order.isPaid()) {
        throw new Exception("결제되지 않음");
      }
    }
  }

  static class BadOrderClient {
    void run(Order order) {
      BadOrderProcessor processor = new BadOrderProcessor();

      // Exception 타입으로는 어떤 오류인지 구분할 수 없다
      try {
        processor.processOrder(order);
      } catch (Exception e) {
        System.out.println("오류 발생");
      }
    }
  }

  // -----------------------------------------------------------------------

  // Good: 의미 있는 Unchecked Exception을 정의한다.
  static class InvalidOrderException extends RuntimeException {
    InvalidOrderException(String message) { super(message); }
  }

  static class PaymentRequiredException extends RuntimeException {
    PaymentRequiredException(String message) { super(message); }
  }

  // Good: 검증 책임을 별도 메서드로 분리하고 Unchecked Exception을 던진다.
  // - 예외 타입으로 의미가 명확하다.
  // - throws 선언이 없어 호출자 코드가 간결해진다.
  // - 필요한 경우에만 상위에서 처리할 수 있다.
  static class GoodOrderProcessor {
    public void processOrder(Order order) {
      validateOrder(order);
      validatePayment(order);
    }

    private void validateOrder(Order order) {
      if (order == null) {
        throw new InvalidOrderException("잘못된 주문");
      }
    }

    private void validatePayment(Order order) {
      if (!order.isPaid()) {
        throw new PaymentRequiredException("결제되지 않음");
      }
    }
  }

  static class GoodOrderClient {
    void run(Order order) {
      GoodOrderProcessor processor = new GoodOrderProcessor();

      // 강제 try-catch 없이 정상 흐름만 드러난다
      processor.processOrder(order);
    }
  }
}
