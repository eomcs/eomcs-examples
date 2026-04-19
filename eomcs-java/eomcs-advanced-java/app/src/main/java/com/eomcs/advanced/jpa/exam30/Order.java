package com.eomcs.advanced.jpa.exam30;

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

// exam30 - 읽기 전용 트랜잭션 & 성능 측정: Order 엔티티
//
@Entity
@Table(name = "shop_orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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

  public Long getId()                       { return id; }
  public Customer getCustomer()             { return customer; }
  public void setCustomer(Customer v)       { this.customer = v; }
  public String getOrderStatus()            { return orderStatus; }
  public void setOrderStatus(String v)      { this.orderStatus = v; }
  public LocalDateTime getOrderDate()       { return orderDate; }
  public void setOrderDate(LocalDateTime v) { this.orderDate = v; }
  public LocalDateTime getCreatedAt()       { return createdAt; }
  public LocalDateTime getUpdatedAt()       { return updatedAt; }

  @Override
  public String toString() {
    return String.format("Order{id=%d, status='%s', date=%s}", id, orderStatus, orderDate);
  }
}
