package com.eomcs.advanced.jpa.exam14;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

// exam14 - 자기 참조 연관관계 (Self-Join)
//
// shop_category.parent_id → shop_category.id
//   전자제품(1) ← 노트북(2) ← 게이밍노트북(3)
//   의류(4)     ← 남성의류(5)
//
// @ManyToOne (parent):
//   - 현재 카테고리의 부모 카테고리를 참조한다.
//   - 같은 테이블(shop_category)을 자기 참조(Self-Join)한다.
//   - 루트 카테고리는 parent = null 이다.
//
// @OneToMany (children):
//   - 현재 카테고리의 자식 카테고리 목록
//   - mappedBy = "parent": parent 필드가 FK 주인이다.
//
// CascadeType.PERSIST:
//   - 부모를 persist할 때 자식도 함께 persist된다.
//   - CascadeType.REMOVE를 추가하면 부모 삭제 시 자식도 삭제되므로 주의가 필요하다.
//
@Entity
@Table(name = "shop_category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  // 자기 참조: 부모 카테고리 (FK = parent_id)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  // 자기 참조: 자식 카테고리 목록
  // CascadeType.PERSIST: 부모 저장 시 자식 목록도 함께 저장
  @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<Category> children = new ArrayList<>();

  public Category() {}

  public Category(String name) {
    this.name = name;
  }

  public Long getId()                         { return id; }
  public String getName()                     { return name; }
  public void setName(String v)               { this.name = v; }
  public Category getParent()                 { return parent; }
  public void setParent(Category v)           { this.parent = v; }
  public List<Category> getChildren()         { return children; }

  // 편의 메서드: 자식 추가 시 양방향 동기화
  public void addChild(Category child) {
    children.add(child);
    child.setParent(this);
  }

  public boolean isRoot() {
    return parent == null;
  }

  @Override
  public String toString() {
    return String.format("Category{id=%d, name='%s', parent=%s}",
        id, name, parent != null ? parent.getName() : "null");
  }
}
