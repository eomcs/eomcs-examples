package com.eomcs.cleancode.ch10.exam01;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch10.exam01.BadAndGood1.BadOrderService;
import com.eomcs.cleancode.ch10.exam01.BadAndGood1.FakeDiscountPolicy;
import com.eomcs.cleancode.ch10.exam01.BadAndGood1.FakeOrderRepository;
import com.eomcs.cleancode.ch10.exam01.BadAndGood1.Order;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - OrderService
//
// 문제점:
// - public 필드를 직접 설정한다 (service.repository = ..., service.discountPolicy = ...).
// - 내부 동작인 applyDiscount()를 외부에서 직접 호출한다.
// - 호출자가 save() 전에 applyDiscount()를 먼저 호출해야 한다는 사실을 알아야 한다.
// - 클래스 내부 구현이 외부 코드에 새어 나간다.
class OrderServiceBadTest {

  @Test
  void 할인을_적용하고_저장한다() {
    BadOrderService service = new BadOrderService();
    FakeOrderRepository repository = new FakeOrderRepository();
    service.repository = repository;                    // public 필드 직접 설정
    service.discountPolicy = new FakeDiscountPolicy(10_000); // public 필드 직접 설정

    Order order = new Order(100_000);
    service.applyDiscount(order);   // 내부 동작을 직접 호출
    service.save(order);            // 저장 별도 호출

    assertTrue(repository.wasSaved());
    assertEquals(90_000, order.totalAmount());
  }
}
