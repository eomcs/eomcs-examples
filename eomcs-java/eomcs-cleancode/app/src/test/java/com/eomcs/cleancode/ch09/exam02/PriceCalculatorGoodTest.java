package com.eomcs.cleancode.ch09.exam02;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam02.BadAndGood3.Coupon;
import com.eomcs.cleancode.ch09.exam02.BadAndGood3.Customer;
import com.eomcs.cleancode.ch09.exam02.BadAndGood3.GoodPriceCalculator;
import com.eomcs.cleancode.ch09.exam02.BadAndGood3.Order;
import com.eomcs.cleancode.ch09.exam02.BadAndGood3.Product;
import org.junit.jupiter.api.Test;

// 예제 3: Tests Enable the -ilities - PriceCalculator
//
// Bad: testPrice() - 하나의 테스트에 VIP 할인, 쿠폰, 음수 방지 등 여러 규칙이 섞여 있다.
//   → 테스트가 부족해 BadPriceCalculator를 리팩터링하기 두렵다.
//
// Good: 규칙별로 테스트를 분리해 GoodPriceCalculator의 리팩터링을 안전하게 지원한다.
//   → 테스트가 변경에 대한 안전망이 되어 설계 개선이 가능해진다.
class PriceCalculatorGoodTest {

  private final GoodPriceCalculator calculator = new GoodPriceCalculator();

  @Test
  void 기본_가격은_상품가격과_수량을_곱한다() {
    Order order = order("NORMAL", 10_000, 2, null);

    int price = calculator.calculate(order);

    assertEquals(20_000, price);
  }

  @Test
  void VIP_고객은_10퍼센트_할인을_받는다() {
    Order order = order("VIP", 10_000, 2, null);

    int price = calculator.calculate(order);

    assertEquals(18_000, price);
  }

  @Test
  void 쿠폰_금액만큼_가격을_차감한다() {
    Order order = order("NORMAL", 10_000, 2, new Coupon(3_000));

    int price = calculator.calculate(order);

    assertEquals(17_000, price);
  }

  @Test
  void 최종_가격은_0원보다_작을_수_없다() {
    Order order = order("NORMAL", 10_000, 1, new Coupon(20_000));

    int price = calculator.calculate(order);

    assertEquals(0, price);
  }

  private Order order(String grade, int productPrice, int quantity, Coupon coupon) {
    return new Order(
        new Customer("user@test.com", grade),
        new Product(productPrice),
        quantity,
        coupon
    );
  }
}
