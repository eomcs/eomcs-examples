package com.eomcs.advanced.jpa.exam29;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

// exam29 - 배치 처리 & Bulk 연산: Customer 엔티티
//
// 배치 처리 관련 Hibernate 설정 (JpaConfig 참고):
//   hibernate.jdbc.batch_size  = 50  : JDBC 배치 묶음 크기
//   hibernate.order_inserts    = true: INSERT 순서 정렬로 배치 효율 향상
//   hibernate.order_updates    = true: UPDATE 순서 정렬로 배치 효율 향상
//
@Entity
@Table(name = "shop_customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 200, unique = true)
  private String email;

  @Column(length = 100)
  private String city;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Customer() {}

  public Customer(String name, String email, String city) {
    this.name      = name;
    this.email     = email;
    this.city      = city;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

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

  @Override
  public String toString() {
    return String.format("Customer{id=%d, name='%s', city='%s'}", id, name, city);
  }
}
