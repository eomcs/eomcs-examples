package com.eomcs.cleancode.ch12.exam02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.eomcs.cleancode.ch12.exam02.BadAndGood2.BadOrderService;
import com.eomcs.cleancode.ch12.exam02.BadAndGood2.Item;
import com.eomcs.cleancode.ch12.exam02.BadAndGood2.Order;
import com.eomcs.cleancode.ch12.exam02.BadAndGood2.OrderService;
import org.junit.jupiter.api.Test;

// 예제 2: 테스트가 "올바른 계산 규칙"을 정의한다 - OrderService
//
// Bad: BadOrderService는 수량을 무시하므로 테스트가 실패한다.
// Good: OrderService는 price * quantity를 계산하므로 테스트를 통과한다.
class BadAndGood2Test {

  @Test
  void 수량을_포함한_총_금액을_계산한다() {
    Order order = new Order();
    order.addItem(new Item(1000, 2)); // 2000
    order.addItem(new Item(500, 3)); // 1500

    OrderService service = new OrderService();

    assertEquals(3500, service.totalPrice(order));
  }

  @Test
  void BadOrderService는_수량을_무시하므로_같은_테스트를_통과하지_못한다() {
    Order order = new Order();
    order.addItem(new Item(1000, 2));
    order.addItem(new Item(500, 3));

    BadOrderService badService = new BadOrderService();

    // 수량을 무시하면 1500이 되어 3500과 다르다 — 버그가 있는 구현이다
    assertEquals(3500, badService.totalPrice(order));
  }
}
