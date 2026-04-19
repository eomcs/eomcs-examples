package com.eomcs.advanced.jpa.exam19;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// exam19 - Criteria API에서 사용하는 제품 엔티티
//
@Entity
@Table(name = "shop_product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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
