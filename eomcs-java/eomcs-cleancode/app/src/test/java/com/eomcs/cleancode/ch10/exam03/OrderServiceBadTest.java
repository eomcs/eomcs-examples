package com.eomcs.cleancode.ch10.exam03;

import com.eomcs.cleancode.ch10.exam03.BadAndGood3.BadOrderService;
import com.eomcs.cleancode.ch10.exam03.BadAndGood3.Order;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - OrderService
//
// 문제점:
// - BadOrderService 내부에서 BadMySqlOrderRepository를 직접 생성한다.
// - 저장소를 교체할 방법이 없어 테스트에서 실제 DB 연결이 필요해진다.
// - 실제 환경 없이는 동작 여부를 검증하기 어렵다.
// - 저장 방식이 바뀌면 BadOrderService 코드를 열어 수정해야 한다.
class OrderServiceBadTest {

  @Test
  void 주문을_저장한다() {
    BadOrderService service = new BadOrderService();
    Order order = new Order("order-1");

    // MySqlOrderRepository가 내부에 고정되어 있어
    // 저장이 실제로 됐는지 검증할 수단이 없다
    service.place(order);
    // assert 불가 — 결과 확인 방법이 없음
  }
}
