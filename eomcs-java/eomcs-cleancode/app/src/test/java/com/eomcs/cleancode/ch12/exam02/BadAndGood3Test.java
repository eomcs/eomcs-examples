package com.eomcs.cleancode.ch12.exam02;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch12.exam02.BadAndGood3.After;
import com.eomcs.cleancode.ch12.exam02.BadAndGood3.Before;
import org.junit.jupiter.api.Test;

// 예제 3: 리팩토링과의 관계
//
// 리팩토링 전후에 동일한 테스트가 통과한다.
// 테스트가 보호하는 상태에서 구조를 바꾸는 것이 안전한 리팩토링이다.
class BadAndGood3Test {

  @Test
  void 리팩토링_전_구현이_올바른_총_금액을_반환한다() {
    Before.Order order = new Before.Order();
    order.addItem(new Before.Item(1000, 2)); // 2000
    order.addItem(new Before.Item(500, 1));  // 500

    Before.OrderService service = new Before.OrderService();

    assertEquals(2500, service.totalPrice(order));
  }

  @Test
  void 리팩토링_후_구현도_동일한_총_금액을_반환한다() {
    After.Order order = new After.Order();
    order.addItem(new After.Item(1000, 2)); // 2000
    order.addItem(new After.Item(500, 1));  // 500

    After.OrderService service = new After.OrderService();

    // 구조는 바뀌었지만 동작은 동일 — 이것이 Emergent Design의 핵심
    assertEquals(2500, service.totalPrice(order));
  }
}
