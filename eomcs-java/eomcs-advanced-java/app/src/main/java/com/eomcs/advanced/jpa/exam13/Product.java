package com.eomcs.advanced.jpa.exam13;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// exam13 - 다대다(@ManyToMany): Product (주인 쪽)
//
// @ManyToMany:
//   - Product ↔ Category 다대다 관계
//   - 관계형 DB에서는 중간 테이블(shop_product_category)이 필요하다.
//   - JPA는 @JoinTable로 중간 테이블을 자동 관리한다.
//
// @JoinTable:
//   - name: 중간 테이블명
//   - joinColumns: 현재 엔티티(Product)를 가리키는 FK 컬럼
//   - inverseJoinColumns: 반대편 엔티티(Category)를 가리키는 FK 컬럼
//
// 주인 선택 기준:
//   - @JoinTable을 소유하는 쪽이 주인이다.
//   - 보통 더 많이 탐색하는 쪽(Product → Category)을 주인으로 설정한다.
//
// @ManyToMany의 한계:
//   - 중간 테이블에 추가 컬럼(등록일, 수량 등)을 넣을 수 없다.
//   - 추가 속성이 필요하면 App2.java처럼 연결 엔티티(ProductCategory)로 리팩토링해야 한다.
//
@Entity
@Table(name = "shop_product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "dtype", nullable = false, length = 20)
  private String dtype = "PHYSICAL";

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

  // 다대다 주인: @JoinTable로 중간 테이블 정의
  @ManyToMany
  @JoinTable(
      name = "shop_product_category",
      joinColumns        = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private List<Category> categories = new ArrayList<>();

  public Product() {}

  public Long getId()                           { return id; }
  public String getDtype()                      { return dtype; }
  public void setDtype(String v)                { this.dtype = v; }
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
  public List<Category> getCategories()         { return categories; }

  // 양방향 연관관계 편의 메서드
  public void addCategory(Category category) {
    categories.add(category);
    category.getProducts().add(this);
  }

  @Override
  public String toString() {
    return String.format("Product{id=%d, name='%s', price=%s}", id, name, price);
  }
}
