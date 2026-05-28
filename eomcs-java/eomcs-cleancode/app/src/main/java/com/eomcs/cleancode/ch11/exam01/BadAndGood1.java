package com.eomcs.cleancode.ch11.exam01;

// 예제 1: 시스템 제작과 시스템 사용을 분리하라 - OrderService / PaymentGateway
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Order {

    private final int totalPrice;

    Order(int totalPrice) {
      this.totalPrice = totalPrice;
    }

    int totalPrice() { return totalPrice; }
  }

  interface PaymentGateway {
    void pay(int amount);
  }

  static class KakaoPayGateway implements PaymentGateway {

    @Override
    public void pay(int amount) {
      System.out.println("Pay with KakaoPay: " + amount);
    }
  }

  // Bad: OrderService가 KakaoPayGateway 생성까지 담당한다
  //   - 비즈니스 로직과 생성 로직이 섞여 있다
  //   - 다른 결제 수단으로 바꾸기 어렵다
  //   - 테스트에서 가짜 결제 객체를 넣기 어렵다
  static class BadOrderService {

    private PaymentGateway paymentGateway;

    public void place(Order order) {
      if (paymentGateway == null) {
        paymentGateway = new KakaoPayGateway(); // 생성 + 사용이 섞임
      }
      paymentGateway.pay(order.totalPrice());
    }
  }

  // Good: OrderService는 결제 객체를 만들지 않는다
  //   - 이미 준비된 PaymentGateway를 사용만 한다
  //   - 생성 책임이 사라져 비즈니스 로직이 단순해진다
  //   - 테스트하기 쉬워진다
  static class OrderService {

    private final PaymentGateway paymentGateway;

    OrderService(PaymentGateway paymentGateway) {
      this.paymentGateway = paymentGateway;
    }

    public void place(Order order) {
      paymentGateway.pay(order.totalPrice());
    }
  }
}
