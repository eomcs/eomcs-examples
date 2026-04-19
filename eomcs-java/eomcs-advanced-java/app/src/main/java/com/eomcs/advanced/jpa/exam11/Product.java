package com.eomcs.advanced.jpa.exam11;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// exam11 - 상속 매핑 전략: 부모 엔티티 (JOINED 전략 적용)
//
// JPA 상속 매핑 3가지 전략 비교:
//
//   SINGLE_TABLE (단일 테이블 전략):
//     - 부모/자식 모두 하나의 테이블에 저장
//     - dtype 컬럼으로 타입 구분
//     - 장점: JOIN 없이 빠른 조회, 단순
//     - 단점: 자식 전용 컬럼이 NULL 허용, 테이블이 거대해짐
//     - @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//
//   JOINED (조인 테이블 전략, 이 예제에서 사용):
//     - 부모 테이블 + 자식별 테이블로 정규화
//     - 조회 시 JOIN 실행
//     - 장점: 정규화된 테이블, NULL 없음
//     - 단점: 조회 시 JOIN 비용 발생
//     - @Inheritance(strategy = InheritanceType.JOINED)
//
//   TABLE_PER_CLASS (구체 클래스별 테이블 전략):
//     - 자식 엔티티마다 독립된 테이블 (부모 컬럼 포함)
//     - 부모 테이블 없음
//     - 장점: 특정 타입 단독 조회 시 빠름
//     - 단점: 다형성 조회 시 UNION → 느림, 컬럼 중복
//     - @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//
// 현재 DB 구조:
//   shop_product (부모) + shop_physical_product + shop_digital_product
//   → JOINED 전략에 맞는 테이블 설계
//
// @DiscriminatorColumn:
//   - 어떤 자식 타입인지 구분하는 컬럼
//   - JOINED 전략에서는 선택적이지만 명시하면 조회 성능에 도움이 된다.
//
@Entity
@Table(name = "shop_product")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 200)
  private String name;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal price;

  @Column(nullable = false)
  private int stock;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Product() {}

  public Long getId()                           { return id; }
  public String getName()                       { return name; }
  public void setName(String v)                 { this.name = v; }
  public BigDecimal getPrice()                  { return price; }
  public void setPrice(BigDecimal v)            { this.price = v; }
  public int getStock()                         { return stock; }
  public void setStock(int v)                   { this.stock = v; }
  public LocalDateTime getCreatedAt()           { return createdAt; }
  public void setCreatedAt(LocalDateTime v)     { this.createdAt = v; }
  public LocalDateTime getUpdatedAt()           { return updatedAt; }
  public void setUpdatedAt(LocalDateTime v)     { this.updatedAt = v; }

  @Override
  public String toString() {
    return String.format("Product{id=%d, name='%s', price=%s, stock=%d}",
        id, name, price, stock);
  }
}
