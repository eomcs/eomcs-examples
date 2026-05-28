package com.eomcs.cleancode.ch09.exam01;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam01.BadAndGood3.GoodOrder;
import com.eomcs.cleancode.ch09.exam01.BadAndGood3.OrderStatus;
import org.junit.jupiter.api.Test;

// TDD 단계별 테스트 - Order
//
// 1단계: 주문은_상품명과_수량으로_생성된다 → Order 없으므로 컴파일 실패
// 2단계: productName, quantity만 구현 → 첫 번째 테스트 통과
// 3단계: 주문의_초기_상태는_CREATED이다 추가 → status 없으므로 컴파일 실패
// 4단계: status = OrderStatus.CREATED 추가 → 두 테스트 모두 통과
// (totalPrice는 아직 테스트로 요구되지 않았으므로 구현하지 않는다)
class OrderTest {

  // 1단계: 실패하는 첫 번째 테스트
  // → 하나의 테스트에 두 가지 검증만 포함한다 (TDD 두 번째 법칙)
  @Test
  void 주문은_상품명과_수량으로_생성된다() {
    GoodOrder order = new GoodOrder("BOOK", 2);

    assertEquals("BOOK", order.getProductName());
    assertEquals(2, order.getQuantity());
  }

  // 3단계: 다음 실패 테스트 - status가 없으면 컴파일 실패
  @Test
  void 주문의_초기_상태는_CREATED이다() {
    GoodOrder order = new GoodOrder("BOOK", 2);

    assertEquals(OrderStatus.CREATED, order.getStatus());
  }
}
