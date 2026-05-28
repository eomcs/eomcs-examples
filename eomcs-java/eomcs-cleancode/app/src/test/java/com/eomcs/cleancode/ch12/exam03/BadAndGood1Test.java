package com.eomcs.cleancode.ch12.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch12.exam03.BadAndGood1.Cart;
import com.eomcs.cleancode.ch12.exam03.BadAndGood1.Item;
import com.eomcs.cleancode.ch12.exam03.BadAndGood1.Order;
import com.eomcs.cleancode.ch12.exam03.BadAndGood1.OrderService;
import org.junit.jupiter.api.Test;

// 예제 1: 중복을 제거하라
//
// 중복된 price*quantity 계산식을 Item.totalPrice()로 추출하고
// 합계 계산을 calculateTotal()로 모으면 동작은 동일하게 유지된다.
class BadAndGood1Test {

  @Test
  void 주문_총_금액을_계산한다() {
    Order order = new Order();
    order.addItem(new Item(1000, 2)); // 2000
    order.addItem(new Item(500, 3));  // 1500

    OrderService service = new OrderService();

    assertEquals(3500, service.calculateOrderTotal(order));
  }

  @Test
  void 장바구니_총_금액을_계산한다() {
    Cart cart = new Cart();
    cart.addItem(new Item(2000, 1)); // 2000
    cart.addItem(new Item(300, 4));  // 1200

    OrderService service = new OrderService();

    assertEquals(3200, service.calculateCartTotal(cart));
  }
}
