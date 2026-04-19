package com.eomcs.advanced.jpa.exam09;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

// exam09 - 연관관계 매핑: Order (다(N) 쪽, 연관관계 주인)
//
// @ManyToOne:
// - 주문 여러 개 → 고객 1명 관계 (N:1)
// - 연관관계의 '주인': FK(customer_id)를 가진 쪽 → Order가 주인
// - 주인이 연관관계 변경을 담당한다 (INSERT/UPDATE 시 FK 값 반영).
//
// @JoinColumn:
// - FK 컬럼명을 지정한다. 생략하면 "필드명_참조테이블PK명" 규칙으로 자동 생성.
// - name = "customer_id": shop_orders.customer_id 컬럼을 FK로 사용한다.
//
// fetch = FetchType.LAZY:
// - 주문 조회 시 고객을 즉시 로딩하지 않는다.
// - order.getCustomer()를 실제로 사용할 때 SELECT가 실행된다.
// - @ManyToOne의 기본값은 EAGER이지만, 성능 최적화를 위해 LAZY를 권장한다.
//
// 테이블명: shop_orders (ORDER는 SQL 예약어이므로 orders 사용)
//
@Entity
@Table(name = "shop_orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @ManyToOne: N(주문)쪽에서 1(고객)쪽을 참조
  // FK 컬럼: shop_orders.customer_id → shop_customer.id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @Column(name = "order_status", nullable = false, length = 20)
  private String orderStatus;

  @Column(name = "order_date", nullable = false)
  private LocalDateTime orderDate;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Order() {}

  public Long getId()                         { return id; }
  public Customer getCustomer()               { return customer; }
  public void setCustomer(Customer v)         { this.customer = v; }
  public String getOrderStatus()              { return orderStatus; }
  public void setOrderStatus(String v)        { this.orderStatus = v; }
  public LocalDateTime getOrderDate()         { return orderDate; }
  public void setOrderDate(LocalDateTime v)   { this.orderDate = v; }
  public LocalDateTime getCreatedAt()         { return createdAt; }
  public void setCreatedAt(LocalDateTime v)   { this.createdAt = v; }
  public LocalDateTime getUpdatedAt()         { return updatedAt; }
  public void setUpdatedAt(LocalDateTime v)   { this.updatedAt = v; }

  @Override
  public String toString() {
    return String.format("Order{id=%d, status='%s', date=%s}",
        id, orderStatus, orderDate);
  }
}
