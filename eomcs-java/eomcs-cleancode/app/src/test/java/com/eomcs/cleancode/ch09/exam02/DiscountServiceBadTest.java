package com.eomcs.cleancode.ch09.exam02;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam02.BadAndGood2.Customer;
import com.eomcs.cleancode.ch09.exam02.BadAndGood2.DiscountService;
import com.eomcs.cleancode.ch09.exam02.BadAndGood2.Order;
import com.eomcs.cleancode.ch09.exam02.BadAndGood2.Product;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - DiscountService
//
// 문제점:
// - VIP 할인과 일반 고객 가격을 한 테스트에서 함께 검증한다.
// - 변수명 c1, p1, o1, r1이 의미를 드러내지 않는다.
// - 테스트가 실패했을 때 어느 고객 조건이 실패했는지 알기 어렵다.
// - 중복 코드가 많아 테스트 유지보수가 어렵다.
class DiscountServiceBadTest {

  @Test
  void discountTest() {
    Customer c1 = new Customer("kim@test.com", "VIP");
    Product p1 = new Product("NOTEBOOK", 1000000);
    Order o1 = new Order(c1, p1, 1);

    DiscountService s = new DiscountService();

    int r1 = s.discount(o1);

    assertEquals(900000, r1);

    Customer c2 = new Customer("lee@test.com", "NORMAL");
    Product p2 = new Product("NOTEBOOK", 1000000);
    Order o2 = new Order(c2, p2, 1);

    int r2 = s.discount(o2);

    assertEquals(1000000, r2);
  }
}
