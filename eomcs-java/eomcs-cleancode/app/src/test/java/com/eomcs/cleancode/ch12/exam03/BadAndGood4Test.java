package com.eomcs.cleancode.ch12.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch12.exam03.BadAndGood4.After;
import com.eomcs.cleancode.ch12.exam03.BadAndGood4.Before;
import org.junit.jupiter.api.Test;

// 예제 4: 리팩토링의 순서
//
// 1단계(for 루프), 2단계(스트림+람다), 3단계(Item::totalPrice) 모두
// 동일한 테스트를 통과한다.
// 테스트가 보호하는 상태에서만 구조를 안전하게 바꿀 수 있다.
class BadAndGood4Test {

  @Test
  void Step1_for루프_구현이_올바른_총_금액을_반환한다() {
    Before.Order order = new Before.Order();
    order.addItem(new Before.Item(1000, 2)); // 2000
    order.addItem(new Before.Item(500, 1));  // 500

    assertEquals(2500, new Before.Step1OrderService().totalPrice(order));
  }

  @Test
  void Step2_스트림_람다_구현이_올바른_총_금액을_반환한다() {
    Before.Order order = new Before.Order();
    order.addItem(new Before.Item(1000, 2)); // 2000
    order.addItem(new Before.Item(500, 1));  // 500

    assertEquals(2500, new Before.Step2OrderService().totalPrice(order));
  }

  @Test
  void Step3_메서드참조_구현이_올바른_총_금액을_반환한다() {
    After.Order order = new After.Order();
    order.addItem(new After.Item(1000, 2)); // 2000
    order.addItem(new After.Item(500, 1));  // 500

    assertEquals(2500, new After.OrderService().totalPrice(order));
  }
}
