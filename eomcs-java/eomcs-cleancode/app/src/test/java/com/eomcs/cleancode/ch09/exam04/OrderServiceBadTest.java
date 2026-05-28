package com.eomcs.cleancode.ch09.exam04;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam04.BadAndGood2.Order;
import com.eomcs.cleancode.ch09.exam04.BadAndGood2.OrderService;
import com.eomcs.cleancode.ch09.exam04.BadAndGood2.OrderStatus;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - OrderService
//
// 문제점:
// - 총액 계산, 결제 상태, 저장 여부, 알림 발송을 모두 검증한다.
// - 테스트가 여러 책임을 가진다.
// - 실패 시 어떤 요구사항이 깨졌는지 파악하기 어렵다.
// - 기능이 변경될 때 테스트가 자주 깨진다.
class OrderServiceBadTest {

  @Test
  void 주문을_처리한다() {
    Order order = new Order("BOOK", 2, 10_000);
    OrderService service = new OrderService();

    service.place(order);

    assertEquals(20_000, order.getTotalPrice());
    assertEquals(OrderStatus.PAID, order.getStatus());
    assertTrue(order.isSaved());
    assertTrue(order.isNotificationSent());
  }
}
