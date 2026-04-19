package com.eomcs.advanced.jpa.exam15;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.math.BigDecimal;

// exam15 - 복합 키 매핑: @IdClass 방식
//
// @IdClass(OrderItemId.class):
//   - 복합 PK 클래스를 지정한다.
//   - 엔티티에 @Id를 여러 개 선언하고, PK 클래스 필드명과 일치시킨다.
//
// shop_order_item 테이블:
//   PK = (order_id, product_id) 복합 키
//   price: 주문 시점 가격 스냅샷
//
// @IdClass vs @EmbeddedId 비교:
//   @IdClass  : PK 필드가 엔티티에 직접 노출 → 단순, JPQL 접근 편리
//               예: WHERE oi.orderId = :id
//   @EmbeddedId: PK 필드가 별도 객체로 묶임 → 명시적, 복합 키 재사용 가능
//               예: WHERE oi.id.orderId = :id  (App2.java 참고)
//
@Entity
@Table(name = "shop_order_item")
@IdClass(OrderItemId.class)
public class OrderItem {

  @Id
  @Column(name = "order_id")
  private Long orderId;

  @Id
  @Column(name = "product_id")
  private Long productId;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal price;

  public OrderItem() {}

  public Long getOrderId()               { return orderId; }
  public void setOrderId(Long v)         { this.orderId = v; }
  public Long getProductId()             { return productId; }
  public void setProductId(Long v)       { this.productId = v; }
  public int getQuantity()               { return quantity; }
  public void setQuantity(int v)         { this.quantity = v; }
  public BigDecimal getPrice()           { return price; }
  public void setPrice(BigDecimal v)     { this.price = v; }

  @Override
  public String toString() {
    return String.format(
        "OrderItem{orderId=%d, productId=%d, qty=%d, price=%s}",
        orderId, productId, quantity, price);
  }
}
