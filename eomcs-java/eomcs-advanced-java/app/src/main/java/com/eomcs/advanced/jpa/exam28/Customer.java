package com.eomcs.advanced.jpa.exam28;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// exam28 - 2차 캐시: Customer 엔티티
//
// @Cache: Hibernate 2차 캐시(L2C)에 이 엔티티를 저장한다.
//   usage = READ_WRITE: 읽기/쓰기 모두 캐시 사용, 낙관적 락으로 동시성 제어
//
// 2차 캐시 동작:
//   첫 번째 findById() → DB SELECT → 결과를 L2C에 저장
//   두 번째 findById() → L2C에서 조회 (DB 쿼리 없음) → 캐시 히트
//
// CacheConcurrencyStrategy 전략:
//   READ_ONLY   : 변경 없는 데이터 (코드 테이블 등) - 가장 빠름
//   READ_WRITE  : 읽기/쓰기 모두 캐시, 낙관적 잠금 - 일반적 용도
//   NONSTRICT_READ_WRITE : 짧은 불일치 허용, 성능↑
//   TRANSACTIONAL: JTA 트랜잭션 기반, 완전한 일관성
//
@Entity
@Table(name = "shop_customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

  @Override
  public String toString() {
    return String.format("Customer{id=%d, name='%s', city='%s'}", id, name, city);
  }
}
