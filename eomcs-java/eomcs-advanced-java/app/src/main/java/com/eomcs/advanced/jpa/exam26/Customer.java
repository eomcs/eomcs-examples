package com.eomcs.advanced.jpa.exam26;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// exam26 - N+1 문제: Customer (일(1) 쪽)
//
// @OneToMany(fetch = LAZY): 기본값 - 주문 목록을 실제로 접근할 때 SELECT 실행
// N+1 문제 발생 지점: findAll()로 고객 목록을 가져온 뒤
//   각 고객의 getOrders()를 호출할 때마다 추가 쿼리가 실행된다.
//
@Entity
@Table(name = "shop_customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 200)
  private String email;

  @Column(length = 100)
  private String city;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  // N+1 원인: LAZY 로딩 상태에서 루프로 접근 시 Customer마다 쿼리 추가 실행
  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<Order> orders = new ArrayList<>();

  public Customer() {}

  public Long getId()                       { return id; }
  public String getName()                   { return name; }
  public void setName(String v)             { this.name = v; }
  public String getEmail()                  { return email; }
  public void setEmail(String v)            { this.email = v; }
  public String getCity()                   { return city; }
  public void setCity(String v)             { this.city = v; }
  public LocalDateTime getCreatedAt()       { return createdAt; }
  public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
  public LocalDateTime getUpdatedAt()       { return updatedAt; }
  public void setUpdatedAt(LocalDateTime v) { this.updatedAt = v; }
  public List<Order> getOrders()            { return orders; }

  @Override
  public String toString() {
    return String.format("Customer{id=%d, name='%s', city='%s'}", id, name, city);
  }
}
