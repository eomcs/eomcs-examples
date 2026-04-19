package com.eomcs.advanced.jpa.exam13;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

// exam13 - @ManyToMany → 연결 엔티티로 리팩토링
//
// @ManyToMany의 한계:
//   - 중간 테이블에 추가 컬럼(예: 등록일)을 넣을 수 없다.
//   - 중간 테이블을 직접 엔티티로 만들면 추가 속성을 관리할 수 있다.
//
// 리팩토링 결과:
//   Product --@OneToMany→ ProductCategory ←@ManyToOne-- Category
//
// @EmbeddedId:
//   - 복합 PK(product_id + category_id)를 @Embeddable 클래스로 관리
//
// @MapsId("productId"):
//   - @EmbeddedId의 productId 필드와 @ManyToOne FK를 함께 관리한다.
//   - product 필드의 id가 복합 PK의 productId로 사용된다.
//
@Entity
@Table(name = "shop_product_category")
public class ProductCategory {

  @EmbeddedId
  private ProductCategoryId id = new ProductCategoryId();

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("categoryId")
  @JoinColumn(name = "category_id")
  private Category category;

  // 연결 엔티티에 추가 속성을 넣을 수 있다는 점이 @ManyToMany 대비 장점
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public ProductCategory() {}

  public ProductCategory(Product product, Category category) {
    this.product   = product;
    this.category  = category;
    this.id        = new ProductCategoryId(product.getId(), category.getId());
    this.createdAt = LocalDateTime.now();
  }

  public ProductCategoryId getId()             { return id; }
  public Product getProduct()                  { return product; }
  public void setProduct(Product v)            { this.product = v; }
  public Category getCategory()                { return category; }
  public void setCategory(Category v)          { this.category = v; }
  public LocalDateTime getCreatedAt()          { return createdAt; }
  public void setCreatedAt(LocalDateTime v)    { this.createdAt = v; }

  @Override
  public String toString() {
    return String.format("ProductCategory{product='%s', category='%s', createdAt=%s}",
        product != null ? product.getName() : "null",
        category != null ? category.getName() : "null",
        createdAt);
  }
}
