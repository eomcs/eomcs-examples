package com.eomcs.advanced.jpa.exam26;

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

// exam26 - N+1 문제: Order (다(N) 쪽, 연관관계 주인)
//
// @ManyToOne(fetch = LAZY): shop_orders.customer_id → shop_customer.id
// 연관관계 주인: FK(customer_id)를 가진 Order가 주인
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
