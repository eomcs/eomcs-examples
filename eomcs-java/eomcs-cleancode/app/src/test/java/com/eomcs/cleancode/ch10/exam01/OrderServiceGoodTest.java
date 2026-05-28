package com.eomcs.cleancode.ch10.exam01;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch10.exam01.BadAndGood1.FakeDiscountPolicy;
import com.eomcs.cleancode.ch10.exam01.BadAndGood1.FakeOrderRepository;
import com.eomcs.cleancode.ch10.exam01.BadAndGood1.Order;
import com.eomcs.cleancode.ch10.exam01.BadAndGood1.OrderService;
import org.junit.jupiter.api.Test;

// 예제 1: 클래스 체계 - OrderService
//
// Bad: public 필드 직접 접근, 내부 메서드(applyDiscount) 직접 호출
// Good: 생성자 주입으로 의존성 주입, public 메서드(save)만 호출.
//       validate → applyDiscount → repository.save 순서는 클래스 내부에서 처리된다.
//       public 메서드 바로 아래에 관련 private 메서드를 배치해 위에서 아래로 읽힌다.
class OrderServiceGoodTest {

  @Test
  void 주문하면_할인이_적용되고_저장된다() {
    FakeOrderRepository repository = new FakeOrderRepository();
    OrderService service = new OrderService(repository, new FakeDiscountPolicy(10_000));

    Order order = new Order(100_000);
    service.save(order);  // 내부적으로 validate → applyDiscount → save 처리

    assertTrue(repository.wasSaved());
    assertEquals(90_000, order.totalAmount());
  }

  @Test
  void 주문_금액이_한도를_초과하면_예외를_던진다() {
    OrderService service = new OrderService(
        new FakeOrderRepository(),
        new FakeDiscountPolicy(0)
    );
    Order order = new Order(OrderService.MAX_ORDER_AMOUNT + 1);

    assertThrows(IllegalArgumentException.class, () -> service.save(order));
  }
}
