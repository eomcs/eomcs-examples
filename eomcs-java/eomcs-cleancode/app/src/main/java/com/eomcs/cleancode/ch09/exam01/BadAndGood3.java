package com.eomcs.cleancode.ch09.exam01;

// 예제 3: TDD 법칙 세 가지 - Order (테스트를 너무 많이 먼저 작성하는 경우)
public class BadAndGood3 {

  private BadAndGood3() {}

  enum OrderStatus { CREATED, PAID, SHIPPED }

  // Bad: 하나의 테스트가 너무 많은 기능을 요구해서 한꺼번에 구현한 Order.
  // - 아직 필요하지 않은 totalPrice까지 강제로 구현하게 된다.
  // - 실패 원인이 너무 많아진다.
  // - TDD의 두 번째 법칙(실패하기에 충분한 정도만 작성)을 어긴다.
  static class BadOrder {
    private final String productName;
    private final int quantity;
    private final OrderStatus status = OrderStatus.CREATED;
    private final int totalPrice;

    BadOrder(String productName, int quantity) {
      this.productName = productName;
      this.quantity = quantity;
      this.totalPrice = quantity * 10_000; // 테스트 요구에 맞추기 위해 임의로 추정
    }

    String getProductName() { return productName; }
    int getQuantity() { return quantity; }
    OrderStatus getStatus() { return status; }
    int getTotalPrice() { return totalPrice; }
  }

  // -----------------------------------------------------------------------

  // Good: TDD로 만들어진 Order - 테스트가 요구한 만큼만 구현한다.
  // - 1단계: 주문은_상품명과_수량으로_생성된다 → productName, quantity만 구현
  // - 2단계: 주문의_초기_상태는_CREATED이다 → status 추가
  // - totalPrice는 아직 테스트로 요구되지 않았으므로 구현하지 않는다.
  static class GoodOrder {
    private final String productName;
    private final int quantity;
    private final OrderStatus status = OrderStatus.CREATED;

    GoodOrder(String productName, int quantity) {
      this.productName = productName;
      this.quantity = quantity;
    }

    String getProductName() { return productName; }
    int getQuantity() { return quantity; }
    OrderStatus getStatus() { return status; }
  }
}
