package com.eomcs.cleancode.ch10.exam01;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch10.exam01.BadAndGood2.BadPaymentProcessor;
import com.eomcs.cleancode.ch10.exam01.BadAndGood2.FakePaymentGateway;
import com.eomcs.cleancode.ch10.exam01.BadAndGood2.Order;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - PaymentProcessor
//
// 문제점:
// - processor.gateway를 직접 교체한다 — 캡슐화가 깨진다.
// - processor.testMode를 직접 변경한다 — 객체 상태 변경 경로가 불투명해진다.
// - PaymentProcessor의 동작이 어디서 바뀌는지 추적하기 어렵다.
// - 테스트와 운영 코드 모두 내부 구현에 의존한다.
class PaymentProcessorBadTest {

  @Test
  void 실제_모드에서는_결제_게이트웨이를_호출한다() {
    BadPaymentProcessor processor = new BadPaymentProcessor();
    FakePaymentGateway fakeGateway = new FakePaymentGateway();
    processor.gateway = fakeGateway;    // public 필드 직접 교체
    processor.testMode = false;

    processor.pay(new Order(10_000));

    assertTrue(fakeGateway.wasCalled());
  }

  @Test
  void 테스트_모드에서는_결제_게이트웨이를_호출하지_않는다() {
    BadPaymentProcessor processor = new BadPaymentProcessor();
    FakePaymentGateway fakeGateway = new FakePaymentGateway();
    processor.gateway = fakeGateway;
    processor.testMode = true;          // public 필드 직접 변경

    processor.pay(new Order(10_000));

    assertFalse(fakeGateway.wasCalled());
  }
}
