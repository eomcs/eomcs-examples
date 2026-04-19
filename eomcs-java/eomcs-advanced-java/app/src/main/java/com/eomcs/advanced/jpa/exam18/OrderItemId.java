package com.eomcs.advanced.jpa.exam18;

import java.io.Serializable;
import java.util.Objects;

// exam18 - OrderItem 복합 키 클래스 (@IdClass)
//
// @IdClass 요건:
//   1. Serializable 구현
//   2. 기본 생성자 필수
//   3. equals() / hashCode() 재정의 필수
//   4. 필드명이 엔티티의 @Id 필드명과 일치해야 함
//
public class OrderItemId implements Serializable {

  private Long orderId;
  private Long productId;

  public OrderItemId() {}

  public OrderItemId(Long orderId, Long productId) {
    this.orderId   = orderId;
    this.productId = productId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrderItemId that)) return false;
    return Objects.equals(orderId, that.orderId)
        && Objects.equals(productId, that.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, productId);
  }
}
