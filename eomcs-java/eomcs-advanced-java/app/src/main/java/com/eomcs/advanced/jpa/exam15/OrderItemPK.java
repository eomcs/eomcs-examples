package com.eomcs.advanced.jpa.exam15;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// exam15 - 복합 키 매핑: @EmbeddedId 방식의 PK 클래스
//
// @Embeddable:
//   - @EmbeddedId로 사용할 복합 PK 클래스에 붙인다.
//   - 컬럼 매핑(@Column)을 PK 클래스 내부에서 직접 선언한다.
//
// @IdClass 방식과의 차이:
//   - @EmbeddedId: PK가 하나의 객체로 묶임 → JPQL에서 "oi.id.orderId"처럼 접근
//   - @IdClass  : PK 필드가 엔티티에 직접 노출 → JPQL에서 "oi.orderId"처럼 접근
//
@Embeddable
public class OrderItemPK implements Serializable {

  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "product_id")
  private Long productId;

  public OrderItemPK() {}

  public OrderItemPK(Long orderId, Long productId) {
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
    if (!(o instanceof OrderItemPK that)) return false;
    return Objects.equals(orderId, that.orderId)
        && Objects.equals(productId, that.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, productId);
  }

  @Override
  public String toString() {
    return String.format("OrderItemPK{orderId=%d, productId=%d}", orderId, productId);
  }
}
