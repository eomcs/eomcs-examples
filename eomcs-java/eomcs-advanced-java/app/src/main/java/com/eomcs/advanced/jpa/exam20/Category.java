package com.eomcs.advanced.jpa.exam20;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// exam20 - 재귀 CTE 시연을 위한 카테고리 엔티티
//
// shop_category 테이블의 자기 참조 구조 (parent_id)를
// 재귀 CTE(WITH 절)로 조회하는 Native Query 예시에 사용한다.
//
@Entity
@Table(name = "shop_category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(name = "parent_id")
  private Long parentId;

  public Category() {}

  public Long getId()             { return id; }
  public String getName()         { return name; }
  public void setName(String v)   { this.name = v; }
  public Long getParentId()       { return parentId; }
  public void setParentId(Long v) { this.parentId = v; }

  @Override
  public String toString() {
    return String.format("Category{id=%d, name='%s', parentId=%s}", id, name, parentId);
  }
}
