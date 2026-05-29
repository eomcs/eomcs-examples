package com.eomcs.cleancode.ch13.exam09;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.eomcs.cleancode.ch13.exam09.BadAndGood2.Order;
import com.eomcs.cleancode.ch13.exam09.BadAndGood2.OrderProcessor;
import org.junit.jupiter.api.Test;

// 예제 2: 스레드 없는 코드를 먼저 동작시켜라
// POJO로 분리하면 동시성 없이 비즈니스 로직만 검증할 수 있다
class BadAndGood2Test {

  // 스레드 없이 순수 비즈니스 로직만 테스트한다
  //   - 비즈니스 버그와 동시성 버그를 분리해서 잡을 수 있다
  //   - 실패 원인이 명확하다
  @Test
  void processesValidOrderWithoutThreads() {
    Order order = new Order(10_000);
    OrderProcessor processor = new OrderProcessor();

    processor.process(order);

    assertTrue(order.isProcessed());
  }

  @Test
  void doesNotProcessZeroPriceOrder() {
    Order order = new Order(0);
    OrderProcessor processor = new OrderProcessor();

    processor.process(order);

    assertTrue(!order.isProcessed());
  }
}
