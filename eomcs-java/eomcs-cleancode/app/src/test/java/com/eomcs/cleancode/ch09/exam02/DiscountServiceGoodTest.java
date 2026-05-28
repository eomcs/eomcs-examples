package com.eomcs.cleancode.ch09.exam02;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam02.BadAndGood2.Customer;
import com.eomcs.cleancode.ch09.exam02.BadAndGood2.DiscountService;
import com.eomcs.cleancode.ch09.exam02.BadAndGood2.Order;
import com.eomcs.cleancode.ch09.exam02.BadAndGood2.Product;
import org.junit.jupiter.api.Test;

// 예제 2: 깨끗한 테스트 코드 - DiscountService
//
// Bad: discountTest() - VIP 할인과 일반 고객 가격을 한 테스트에서 검증한다.
//   → c1, p1, o1 같은 의미 없는 변수명, 실패 시 어느 조건이 깨졌는지 알기 어렵다.
//
// Good: 테스트를 하나의 개념씩 분리하고, 헬퍼 메서드로 중복 준비 코드를 제거한다.
//   → 테스트 이름만으로 요구사항을 파악할 수 있다.
class DiscountServiceGoodTest {

  @Test
  void VIP_고객은_10퍼센트_할인을_받는다() {
    Order order = orderOf("VIP", 1_000_000);
    DiscountService service = new DiscountService();

    int discountedPrice = service.discount(order);

    assertEquals(900_000, discountedPrice);
  }

  @Test
  void 일반_고객은_할인을_받지_않는다() {
    Order order = orderOf("NORMAL", 1_000_000);
    DiscountService service = new DiscountService();

    int discountedPrice = service.discount(order);

    assertEquals(1_000_000, discountedPrice);
  }

  private Order orderOf(String grade, int price) {
    Customer customer = new Customer("user@test.com", grade);
    Product product = new Product("NOTEBOOK", price);
    return new Order(customer, product, 1);
  }
}
