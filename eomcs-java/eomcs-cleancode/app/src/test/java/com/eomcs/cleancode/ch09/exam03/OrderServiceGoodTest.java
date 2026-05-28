package com.eomcs.cleancode.ch09.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam03.BadAndGood1.Customer;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.FakeOrderRepository;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.FakePaymentGateway;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.Order;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.OrderService;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.OrderStatus;
import com.eomcs.cleancode.ch09.exam03.BadAndGood1.Product;
import org.junit.jupiter.api.Test;

// 예제 1: 깔끔한 테스트 - OrderService
//
// Bad: testOrder() - 이름이 없고, 변수명 불명확, 여러 개념을 한 테스트에서 검증한다.
// Good: 테스트 이름이 요구사항을 문장처럼 설명하고,
//       BUILD/OPERATE/CHECK 구조로 정리하며, 헬퍼 메서드로 준비 코드를 숨겼다.
class OrderServiceGoodTest {

  @Test
  void VIP_고객이_주문하면_할인된_금액으로_결제된다() {
    // BUILD: 테스트 데이터 준비
    Order order = vipOrder("BOOK", 10_000, 2);
    OrderService orderService = orderService();

    // OPERATE: 테스트 대상 실행
    orderService.order(order);

    // CHECK: 결과 검증
    assertEquals(18_000, order.getTotalPrice());
    assertEquals(OrderStatus.PAID, order.getStatus());
  }

  @Test
  void 일반_고객이_주문하면_정가로_결제된다() {
    Order order = normalOrder("BOOK", 10_000, 2);
    OrderService orderService = orderService();

    orderService.order(order);

    assertEquals(20_000, order.getTotalPrice());
    assertEquals(OrderStatus.PAID, order.getStatus());
  }

  private Order vipOrder(String productName, int price, int quantity) {
    Customer vipCustomer = new Customer("kim@test.com", "VIP");
    Product product = new Product(productName, price);
    return new Order(vipCustomer, product, quantity);
  }

  private Order normalOrder(String productName, int price, int quantity) {
    Customer normalCustomer = new Customer("lee@test.com", "NORMAL");
    Product product = new Product(productName, price);
    return new Order(normalCustomer, product, quantity);
  }

  private OrderService orderService() {
    return new OrderService(
        new FakeOrderRepository(),
        new FakePaymentGateway()
    );
  }
}
