package com.eomcs.cleancode.ch09.exam02;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam02.BadAndGood1.Customer;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.FakeNotificationSender;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.FakeOrderRepository;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.FakePaymentGateway;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.Order;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.OrderService;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.OrderStatus;
import com.eomcs.cleancode.ch09.exam02.BadAndGood1.Product;
import org.junit.jupiter.api.Test;

// 예제 1: 깨끗한 테스트 코드 - OrderService
//
// Bad: test() - 이름이 없고, 준비 코드가 길며, 여러 개념을 한 테스트에서 검증한다.
//   → 실패 원인을 찾기 어렵고, 프로덕션 코드 변경 시 수정 비용이 커진다.
//
// Good: 테스트 이름이 의도를 설명하고, 헬퍼 메서드로 준비 코드를 숨겨
//   핵심 흐름(총액 계산 + 결제완료)에 집중한다.
class OrderServiceGoodTest {

  @Test
  void 주문하면_총액을_계산하고_결제완료_상태가_된다() {
    Order order = orderWithBook(2);
    OrderService service = orderService();

    service.order(order);

    assertEquals(20000, order.getTotalPrice());
    assertEquals(OrderStatus.PAID, order.getStatus());
  }

  @Test
  void 주문하면_알림이_발송된다() {
    Order order = orderWithBook(1);
    OrderService service = orderService();

    service.order(order);

    assertTrue(order.isNotificationSent());
  }

  private Order orderWithBook(int quantity) {
    Product book = new Product("BOOK", 10000);
    Customer customer = new Customer("kim@test.com");
    return new Order(customer, book, quantity);
  }

  private OrderService orderService() {
    return new OrderService(
        new FakeOrderRepository(),
        new FakePaymentGateway(),
        new FakeNotificationSender()
    );
  }
}
