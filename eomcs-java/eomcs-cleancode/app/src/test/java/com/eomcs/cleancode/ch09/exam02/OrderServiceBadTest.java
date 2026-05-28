package com.eomcs.cleancode.ch09.exam02;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam02.BadAndGood1.Customer;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.NotificationSender;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.Order;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.OrderRepository;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.OrderService;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.OrderStatus;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.PaymentGateway;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.Product;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - OrderService
//
// 문제점:
// - 테스트 이름(test)이 무엇을 검증하는지 말하지 않는다.
// - 준비 코드가 길어서 핵심 검증이 잘 보이지 않는다.
// - 주문 생성, 결제, 알림까지 여러 개념을 한 테스트에서 검증한다.
// - 테스트가 깨졌을 때 무엇이 문제인지 빠르게 알기 어렵다.
// - 프로덕션 코드가 바뀌면 테스트 수정 비용이 커진다.
class OrderServiceBadTest {

  @Test
  void test() {
    Product product = new Product("BOOK", 10000);
    Customer customer = new Customer("kim@test.com");
    Order order = new Order(customer, product, 2);

    OrderService service = new OrderService(
        new OrderRepository(),
        new PaymentGateway(),
        new NotificationSender()
    );

    service.order(order);

    assertEquals("BOOK", order.getProduct().getName());
    assertEquals(2, order.getQuantity());
    assertEquals(20000, order.getTotalPrice());
    assertEquals(OrderStatus.PAID, order.getStatus());
    assertTrue(order.isNotificationSent());
  }
}
