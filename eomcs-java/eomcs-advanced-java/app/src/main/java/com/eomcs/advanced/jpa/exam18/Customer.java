package com.eomcs.advanced.jpa.exam18;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// exam18 - JPQL 심화에서 사용하는 고객 엔티티
//
// @NamedQuery: 컴파일 시점에 JPQL 문법을 검증하고, 애플리케이션 기동 시 파싱·캐시한다.
//   - 오타·문법 오류를 런타임 전에 조기 발견
//   - em.createNamedQuery("Customer.findAll", Customer.class)로 호출
//
@Entity
@Table(name = "shop_customer")
@NamedQuery(
    name  = "Customer.findAll",
    query = "SELECT c FROM Customer c ORDER BY c.id")
@NamedQuery(
    name  = "Customer.findByCity",
    query = "SELECT c FROM Customer c WHERE c.city = :city ORDER BY c.id")
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

  // 연관관계 주인: Order.customer
  // JOIN FETCH 시연을 위해 지연 로딩 유지
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
    return String.format("Customer{id=%d, name='%s', city='%s'}",
        id, name, city);
  }
}
