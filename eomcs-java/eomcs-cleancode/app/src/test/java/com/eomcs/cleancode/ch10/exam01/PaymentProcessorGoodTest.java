package com.eomcs.cleancode.ch10.exam01;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch10.exam01.BadAndGood2.FakePaymentGateway;
import com.eomcs.cleancode.ch10.exam01.BadAndGood2.Order;
import com.eomcs.cleancode.ch10.exam01.BadAndGood2.PaymentProcessor;
import org.junit.jupiter.api.Test;

// 예제 2: 클래스 체계 - PaymentProcessor
//
// Bad: public 필드(gateway, testMode)를 직접 설정해 캡슐화를 파괴한다.
// Good: 필드는 private, 생성자 주입으로 대체 구현을 넣는다.
//       캡슐화를 유지하면서도 테스트 가능하고, 상태 변경 경로가 명확하다.
class PaymentProcessorGoodTest {

  @Test
  void 주문하면_결제_게이트웨이를_호출한다() {
    FakePaymentGateway fakeGateway = new FakePaymentGateway();
    PaymentProcessor processor = new PaymentProcessor(fakeGateway); // 생성자 주입

    processor.pay(new Order(10_000));

    assertTrue(fakeGateway.wasCalled());
  }
}
