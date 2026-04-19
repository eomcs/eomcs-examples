package com.eomcs.advanced.jpa.exam13;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// exam13 - 연결 엔티티(ProductCategory)의 복합 PK 클래스
//
// shop_product_category 테이블의 PK는 (product_id, category_id) 복합 키이다.
// @Embeddable로 선언하면 ProductCategory에서 @EmbeddedId로 사용할 수 있다.
//
// Serializable 구현 필수: JPA 스펙에서 PK 클래스는 Serializable이어야 한다.
// equals/hashCode 구현 필수: 1차 캐시 조회 및 동일성 보장에 사용된다.
//
@Embeddable
public class ProductCategoryId implements Serializable {

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "category_id")
  private Long categoryId;

  public ProductCategoryId() {}

  public ProductCategoryId(Long productId, Long categoryId) {
    this.productId  = productId;
    this.categoryId = categoryId;
  }

  public Long getProductId()           { return productId; }
  public void setProductId(Long v)     { this.productId = v; }
  public Long getCategoryId()          { return categoryId; }
  public void setCategoryId(Long v)    { this.categoryId = v; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProductCategoryId that)) return false;
    return Objects.equals(productId, that.productId)
        && Objects.equals(categoryId, that.categoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, categoryId);
  }
}
