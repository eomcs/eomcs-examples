package com.eomcs.advanced.jpa.exam11;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.math.BigDecimal;

// exam11 - 실물 제품 (상속 매핑 자식 엔티티)
//
// @DiscriminatorValue("PHYSICAL"):
//   shop_product.dtype = 'PHYSICAL' 인 행이 이 클래스로 매핑된다.
//
// @PrimaryKeyJoinColumn(name = "product_id"):
//   JOINED 전략에서 자식 테이블(shop_physical_product)의 PK 컬럼명을 지정한다.
//   이 PK가 동시에 부모 테이블(shop_product)의 id를 참조하는 FK이기도 하다.
//
// INSERT 시 Hibernate가 실행하는 SQL:
//   1. INSERT INTO shop_product (dtype, name, price, stock, ...) VALUES ('PHYSICAL', ...)
//   2. INSERT INTO shop_physical_product (product_id, weight, shipping_fee) VALUES (?, ?, ?)
//
// SELECT 시 Hibernate가 실행하는 SQL:
//   SELECT p.*, pp.weight, pp.shipping_fee
//   FROM shop_product p
//   INNER JOIN shop_physical_product pp ON p.id = pp.product_id
//   WHERE p.id = ?
//
@Entity
@Table(name = "shop_physical_product")
@DiscriminatorValue("PHYSICAL")
@PrimaryKeyJoinColumn(name = "product_id")
public class PhysicalProduct extends Product {

  @Column(precision = 10, scale = 3)
  private BigDecimal weight;

  @Column(name = "shipping_fee", precision = 10, scale = 2)
  private BigDecimal shippingFee;

  public PhysicalProduct() {}

  public BigDecimal getWeight()          { return weight; }
  public void setWeight(BigDecimal v)    { this.weight = v; }
  public BigDecimal getShippingFee()     { return shippingFee; }
  public void setShippingFee(BigDecimal v) { this.shippingFee = v; }

  @Override
  public String toString() {
    return String.format(
        "PhysicalProduct{id=%d, name='%s', price=%s, weight=%skg, shippingFee=%s원}",
        getId(), getName(), getPrice(), weight, shippingFee);
  }
}
