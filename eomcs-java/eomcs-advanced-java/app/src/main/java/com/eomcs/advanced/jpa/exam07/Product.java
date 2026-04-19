package com.eomcs.advanced.jpa.exam07;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// exam07 - 기본 엔티티 매핑: Product 엔티티
//
// BigDecimal: NUMBER(12, 2) 컬럼처럼 소수점 정밀도가 중요한 금액 필드에 사용한다.
//   double은 이진 부동소수점으로 금액 계산 시 오차가 생길 수 있다.
//   precision=12, scale=2: 소수점 포함 최대 12자리, 소수 2자리.
//
@Entity
@Table(name = "shop_product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 상속 매핑 구분자: 'PHYSICAL' 또는 'DIGITAL'
  // insertable=false, updatable=false: Hibernate가 INSERT/UPDATE 시 이 컬럼을 포함하지 않는다.
  // (exam11 상속 매핑에서 @DiscriminatorColumn으로 관리하게 되면 직접 쓰지 않음)
  @Column(name = "dtype", nullable = false, length = 20)
  private String dtype;

  @Column(name = "name", nullable = false, length = 200)
  private String name;

  // precision/scale: NUMBER(12,2) 컬럼 매핑 시 명시하면 DDL 생성 시 정확히 반영된다.
  @Column(name = "price", nullable = false, precision = 12, scale = 2)
  private BigDecimal price;

  @Column(name = "stock", nullable = false)
  private int stock;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Product() {}

  public Long getId()                        { return id; }
  public String getDtype()                   { return dtype; }
  public void setDtype(String v)             { this.dtype = v; }
  public String getName()                    { return name; }
  public void setName(String v)              { this.name = v; }
  public BigDecimal getPrice()               { return price; }
  public void setPrice(BigDecimal v)         { this.price = v; }
  public int getStock()                      { return stock; }
  public void setStock(int v)                { this.stock = v; }
  public LocalDateTime getCreatedAt()        { return createdAt; }
  public void setCreatedAt(LocalDateTime v)  { this.createdAt = v; }
  public LocalDateTime getUpdatedAt()        { return updatedAt; }
  public void setUpdatedAt(LocalDateTime v)  { this.updatedAt = v; }

  @Override
  public String toString() {
    return String.format("Product{id=%d, dtype='%s', name='%s', price=%s, stock=%d}",
        id, dtype, name, price, stock);
  }
}
