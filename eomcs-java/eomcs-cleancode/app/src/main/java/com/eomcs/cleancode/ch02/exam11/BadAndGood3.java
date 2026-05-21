package com.eomcs.cleancode.ch02.exam11;

public class BadAndGood3 {

  static class User {}
  static class Order {}
  static class Payment {}

  // Bad
  // - UserManager, OrderController, PaymentHandler → 모두 "업무 흐름을 처리하는 객체"인데 접미사가 다르다.
  // - Manager, Controller, Handler가 각각 다른 의미인지 불명확하고, 같은 계층인지 알기 어렵다.
  static class BadUserManager {
    void register(User user) { System.out.println("사용자 등록"); }
    void updateProfile(User user) { System.out.println("프로필 수정"); }
  }

  static class BadOrderController {
    void placeOrder(Order order) { System.out.println("주문 접수"); }
    void cancelOrder(Order order) { System.out.println("주문 취소"); }
  }

  static class BadPaymentHandler {
    void processPayment(Payment payment) { System.out.println("결제 처리"); }
    void refund(Payment payment) { System.out.println("환불 처리"); }
  }

  // Good
  // - UserService, OrderService, PaymentService → 같은 역할의 클래스에 같은 접미사를 사용한다.
  // - 이름만 봐도 세 클래스가 같은 계층임을 즉시 알 수 있다.
  static class GoodUserService {
    void register(User user) { System.out.println("사용자 등록"); }
    void updateProfile(User user) { System.out.println("프로필 수정"); }
  }

  static class GoodOrderService {
    void placeOrder(Order order) { System.out.println("주문 접수"); }
    void cancelOrder(Order order) { System.out.println("주문 취소"); }
  }

  static class GoodPaymentService {
    void processPayment(Payment payment) { System.out.println("결제 처리"); }
    void refund(Payment payment) { System.out.println("환불 처리"); }
  }
}
