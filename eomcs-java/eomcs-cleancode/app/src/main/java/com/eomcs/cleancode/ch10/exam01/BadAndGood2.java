package com.eomcs.cleancode.ch10.exam01;

// 예제 2: 클래스 체계 - PaymentProcessor
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Order {
    private final int amount;

    Order(int amount) {
      this.amount = amount;
    }

    int totalAmount() { return amount; }
  }

  interface PaymentGateway {
    void request(int amount);
  }

  static class FakePaymentGateway implements PaymentGateway {
    private boolean called = false;

    @Override
    public void request(int amount) {
      called = true;
    }

    boolean wasCalled() { return called; }
  }

  // Bad: public 필드로 내부 상태가 외부에 노출됨
  static class BadPaymentProcessor {

    public PaymentGateway gateway;    // 외부에서 직접 교체 가능 - 캡슐화 파괴
    public boolean testMode = false;  // 외부에서 직접 변경 가능 - 동작 예측 불가

    public void pay(Order order) {
      if (testMode) {
        System.out.println("test payment");
        return;
      }
      gateway.request(order.totalAmount());
    }
  }

  // Good: 필드를 private으로 숨기고, 생성자 주입으로 테스트 가능성 확보
  static class PaymentProcessor {

    private final PaymentGateway gateway;

    PaymentProcessor(PaymentGateway gateway) {
      this.gateway = gateway;
    }

    void pay(Order order) {
      gateway.request(order.totalAmount());
    }
  }
}
