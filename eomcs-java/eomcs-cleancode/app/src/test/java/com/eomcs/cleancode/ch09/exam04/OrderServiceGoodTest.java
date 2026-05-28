package com.eomcs.cleancode.ch09.exam04;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam04.BadAndGood2.FakeNotificationSender;
import com.eomcs.cleancode.ch09.exam04.BadAndGood2.Order;
import com.eomcs.cleancode.ch09.exam04.BadAndGood2.OrderService;
import com.eomcs.cleancode.ch09.exam04.BadAndGood2.OrderStatus;
import org.junit.jupiter.api.Test;

// 예제 2: 테스트당 하나의 개념 - OrderService
//
// Bad: 주문을_처리한다() - 총액/결제상태/저장/알림을 한 테스트에서 모두 검증한다.
// Good: 각 동작을 별도 테스트로 분리해 테스트 이름이 요구사항을 직접 표현한다.
//       실패 원인이 분명하고, 변경 영향이 특정 테스트에만 머문다.
class OrderServiceGoodTest {

  @Test
  void 주문하면_총액을_계산한다() {
    Order order = new Order("BOOK", 2, 10_000);
    OrderService service = new OrderService();

    service.place(order);

    assertEquals(20_000, order.getTotalPrice());
  }

  @Test
  void 주문하면_결제완료_상태가_된다() {
    Order order = new Order("BOOK", 2, 10_000);
    OrderService service = new OrderService();

    service.place(order);

    assertEquals(OrderStatus.PAID, order.getStatus());
  }

  @Test
  void 주문하면_알림을_보낸다() {
    FakeNotificationSender notificationSender = new FakeNotificationSender();
    OrderService service = new OrderService(notificationSender);
    Order order = new Order("BOOK", 2, 10_000);

    service.place(order);

    assertTrue(notificationSender.wasSent());
  }
}
