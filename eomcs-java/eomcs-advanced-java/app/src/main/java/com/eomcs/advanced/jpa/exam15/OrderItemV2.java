package com.eomcs.advanced.jpa.exam15;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

// exam15 - 복합 키 매핑: @EmbeddedId 방식
//
// @EmbeddedId:
//   - @Embeddable PK 클래스를 단일 필드로 선언한다.
//   - PK 필드 접근 시 id.orderId, id.productId 형태가 된다.
//
// JPQL 예시:
//   WHERE oi.id.orderId = :orderId     (@EmbeddedId 방식)
//   WHERE oi.orderId    = :orderId     (@IdClass 방식)
//
@Entity
@Table(name = "shop_order_item")
public class OrderItemV2 {

  @EmbeddedId
  private OrderItemPK id;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal price;

  public OrderItemV2() {}

  public OrderItemPK getId()             { return id; }
  public void setId(OrderItemPK v)       { this.id = v; }
  public int getQuantity()               { return quantity; }
  public void setQuantity(int v)         { this.quantity = v; }
  public BigDecimal getPrice()           { return price; }
  public void setPrice(BigDecimal v)     { this.price = v; }

  @Override
  public String toString() {
    return String.format(
        "OrderItemV2{orderId=%d, productId=%d, qty=%d, price=%s}",
        id != null ? id.getOrderId() : null,
        id != null ? id.getProductId() : null,
        quantity, price);
  }
}
