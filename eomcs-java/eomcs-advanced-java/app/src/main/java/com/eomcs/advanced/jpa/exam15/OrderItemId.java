package com.eomcs.advanced.jpa.exam15;

import java.io.Serializable;
import java.util.Objects;

// exam15 - 복합 키 매핑: @IdClass 방식의 PK 클래스
//
// @IdClass 방식:
//   - 엔티티 클래스에 @Id를 여러 개 선언한다.
//   - 별도의 PK 클래스를 @IdClass(OrderItemId.class)로 지정한다.
//   - PK 클래스의 필드명은 엔티티의 @Id 필드명과 일치해야 한다.
//
// PK 클래스 요건 (JPA 스펙):
//   1. public 클래스
//   2. 기본 생성자 필수
//   3. Serializable 구현
//   4. equals() / hashCode() 구현
//
public class OrderItemId implements Serializable {

  private Long orderId;
  private Long productId;

  public OrderItemId() {}

  public OrderItemId(Long orderId, Long productId) {
    this.orderId   = orderId;
    this.productId = productId;
  }

  public Long getOrderId()            { return orderId; }
  public void setOrderId(Long v)      { this.orderId = v; }
  public Long getProductId()          { return productId; }
  public void setProductId(Long v)    { this.productId = v; }

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

  @Override
  public String toString() {
    return String.format("OrderItemId{orderId=%d, productId=%d}", orderId, productId);
  }
}
