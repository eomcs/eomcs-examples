package com.eomcs.advanced.jpa.exam18;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

// exam18 - 복합 키 주문 상품 엔티티 (@IdClass)
//
// order_id + product_id 가 복합 PK (shop_order_item 테이블)
// @ManyToOne 관계는 insertable=false, updatable=false 로 읽기 전용 매핑
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

  // @Id 컬럼과 동일한 컬럼을 @ManyToOne 으로도 매핑 → insertable=false, updatable=false 필수
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", insertable = false, updatable = false)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product product;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal price;

  public OrderItem() {}

  public Long getOrderId()              { return orderId; }
  public Long getProductId()            { return productId; }
  public Order getOrder()               { return order; }
  public Product getProduct()           { return product; }
  public int getQuantity()              { return quantity; }
  public void setQuantity(int v)        { this.quantity = v; }
  public BigDecimal getPrice()          { return price; }
  public void setPrice(BigDecimal v)    { this.price = v; }

  @Override
  public String toString() {
    return String.format("OrderItem{orderId=%d, productId=%d, quantity=%d, price=%s}",
        orderId, productId, quantity, price);
  }
}
