package com.eomcs.advanced.jpa.exam27;

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

// exam27 - 지연/즉시 로딩: Customer (일(1) 쪽)
//
// fetch = FetchType.LAZY (기본값):
//   - orders는 실제 접근 시에만 로드 (프록시 객체로 대기)
//   - 트랜잭션 밖에서 접근하면 LazyInitializationException 발생
//
// fetch = FetchType.EAGER:
//   - Customer 조회 시 orders를 항상 함께 로드 (JOIN)
//   - 사용하지 않아도 항상 로드 → 불필요한 데이터 로드 위험
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

  // LAZY (기본값): getOrders() 최초 접근 시 SELECT 실행
  // 변경 실험: EAGER로 바꾸면 findById() 시 항상 orders JOIN 로드
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
