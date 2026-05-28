package com.eomcs.cleancode.ch09.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam03.BadAndGood1.Customer;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.FakeOrderRepository;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.FakePaymentGateway;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.Order;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.OrderService;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.OrderStatus;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.Product;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - OrderService
//
// 문제점:
// - 테스트 이름(testOrder)이 무엇을 검증하는지 말하지 않는다.
// - p, c, o, pg, r, s 같은 변수명이 의미를 드러내지 않는다.
// - 준비 코드가 길어서 핵심 검증이 잘 보이지 않는다.
// - 할인, 결제, 저장을 한 테스트에서 모두 검증한다.
// - 테스트가 문서처럼 읽히지 않는다.
class OrderServiceBadTest {

  @Test
  void testOrder() {
    Product p = new Product("BOOK", 10000);
    Customer c = new Customer("kim@test.com", "VIP");
    Order o = new Order(c, p, 2);
    FakePaymentGateway pg = new FakePaymentGateway();
    FakeOrderRepository r = new FakeOrderRepository();
    OrderService s = new OrderService(r, pg);

    s.order(o);

    assertEquals(18000, o.getTotalPrice());
    assertEquals(OrderStatus.PAID, o.getStatus());
    assertEquals(1, r.count());
    assertTrue(pg.wasPaid());
  }
}
