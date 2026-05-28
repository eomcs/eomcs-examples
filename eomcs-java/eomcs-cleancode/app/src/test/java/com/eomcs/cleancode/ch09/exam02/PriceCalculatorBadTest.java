package com.eomcs.cleancode.ch09.exam02;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam02.BadAndGood3.BadPriceCalculator;
import com.eomcs.cleancode.ch09.exam02.BadAndGood3.Coupon;
import com.eomcs.cleancode.ch09.exam02.BadAndGood3.Customer;
import com.eomcs.cleancode.ch09.exam02.BadAndGood3.Order;
import com.eomcs.cleancode.ch09.exam02.BadAndGood3.Product;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - PriceCalculator
//
// 문제점:
// - 테스트가 하나뿐이라 리팩터링 시 다른 조건이 깨졌는지 알 수 없다.
// - VIP 할인, 쿠폰, 음수 방지 정책이 한 테스트에 섞여 있다.
// - 테스트가 부족하면 가격 계산 로직을 분리하거나 개선하기 두렵다.
// - 결국 프로덕션 코드를 계속 덧붙이는 방식으로 유지하게 된다.
class PriceCalculatorBadTest {

  @Test
  void testPrice() {
    BadPriceCalculator calculator = new BadPriceCalculator();

    Order order = new Order(
        new Customer("kim@test.com", "VIP"),
        new Product(10000),
        2,
        new Coupon(3000)
    );

    assertEquals(15000, calculator.calculate(order));
  }
}
