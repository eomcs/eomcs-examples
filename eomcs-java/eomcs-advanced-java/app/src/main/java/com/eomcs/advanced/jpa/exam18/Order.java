package com.eomcs.advanced.jpa.exam18;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// exam18 - JOIN FETCH 시연을 위한 주문 엔티티
//
// customer, orderItems 모두 지연 로딩(LAZY) → N+1 문제 발생 구조
// JOIN FETCH로 한 번에 로드하는 것이 핵심 시연 포인트
//
@Entity
@Table(name = "shop_orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // FK: shop_orders.customer_id → shop_customer.id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @Column(name = "order_status", length = 20, nullable = false)
  private String orderStatus;

  @Column(name = "order_date", nullable = false)
  private LocalDateTime orderDate;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  // OrderItem.order 필드가 연관관계 주인
  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
  private List<OrderItem> orderItems = new ArrayList<>();

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
  public List<OrderItem> getOrderItems()      { return orderItems; }

  @Override
  public String toString() {
    return String.format("Order{id=%d, orderStatus='%s', orderDate=%s}",
        id, orderStatus, orderDate);
  }
}
