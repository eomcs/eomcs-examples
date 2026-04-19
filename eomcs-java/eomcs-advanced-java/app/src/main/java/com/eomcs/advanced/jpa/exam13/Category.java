package com.eomcs.advanced.jpa.exam13;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

// exam13 - 다대다(@ManyToMany): Category (비주인 쪽)
//
// mappedBy = "categories":
//   Product.categories 필드가 @JoinTable을 소유하는 주인이다.
//   Category는 비주인이므로 읽기 전용 참조만 갖는다.
//
@Entity
@Table(name = "shop_category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  // 비주인: mappedBy로 주인 필드 지정
  @ManyToMany(mappedBy = "categories")
  private List<Product> products = new ArrayList<>();

  public Category() {}

  public Long getId()                    { return id; }
  public String getName()                { return name; }
  public void setName(String v)          { this.name = v; }
  public List<Product> getProducts()     { return products; }

  @Override
  public String toString() {
    return String.format("Category{id=%d, name='%s'}", id, name);
  }
}
