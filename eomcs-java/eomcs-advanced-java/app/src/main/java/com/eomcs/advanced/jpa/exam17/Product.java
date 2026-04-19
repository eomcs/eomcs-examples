package com.eomcs.advanced.jpa.exam17;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// exam17 - JPQL 기초에서 사용하는 제품 엔티티
//
// shop_product 테이블은 dtype(NOT NULL) 컬럼을 가지며 상속 매핑(exam11)에서 사용된다.
// 이 예제에서는 JPQL 조회 시연을 위해 상속 없이 단일 엔티티로 매핑하며,
// dtype 컬럼은 insertable=false, updatable=false 로 읽기 전용으로 처리한다.
//
@Entity
@Table(name = "shop_product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 기존 DB의 dtype 컬럼 - 읽기 전용 (INSERT/UPDATE 제외)
  @Column(name = "dtype", insertable = false, updatable = false, length = 20)
  private String dtype;

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

  public Long getId()                       { return id; }
  public String getDtype()                  { return dtype; }
  public String getName()                   { return name; }
  public void setName(String v)             { this.name = v; }
  public BigDecimal getPrice()              { return price; }
  public void setPrice(BigDecimal v)        { this.price = v; }
  public int getStock()                     { return stock; }
  public void setStock(int v)               { this.stock = v; }
  public LocalDateTime getCreatedAt()       { return createdAt; }
  public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
  public LocalDateTime getUpdatedAt()       { return updatedAt; }
  public void setUpdatedAt(LocalDateTime v) { this.updatedAt = v; }

  @Override
  public String toString() {
    return String.format("Product{id=%d, dtype='%s', name='%s', price=%s, stock=%d}",
        id, dtype, name, price, stock);
  }
}
