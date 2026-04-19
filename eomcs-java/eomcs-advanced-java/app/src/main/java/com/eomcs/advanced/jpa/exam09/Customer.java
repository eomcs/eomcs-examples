package com.eomcs.advanced.jpa.exam09;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// exam09 - 연관관계 매핑: Customer (일(1) 쪽)
//
// @OneToMany:
// - Customer 1명 → Order 여러 개 관계
// - mappedBy = "customer": Order.customer 필드가 연관관계의 주인(FK 보유)임을 선언한다.
// - 양방향 매핑에서 '주인'은 FK를 가진 쪽(Many 쪽)이다.
// - Customer는 주인이 아니므로 @OneToMany(mappedBy)로 읽기 전용 참조를 갖는다.
//
// mappedBy를 지정하지 않으면 Hibernate가 중간 테이블을 생성하려 한다.
//
// fetch = FetchType.LAZY (기본값):
// - 주문 목록을 실제로 사용할 때 SELECT를 실행한다 (지연 로딩).
// - 기본값이 LAZY이므로 명시하지 않아도 되지만 학습을 위해 명시한다.
//
@Entity
@Table(name = "shop_customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 200)
  private String email;

  @Column(length = 100)
  private String city;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  // 양방향 매핑: Customer → 주문 목록
  // mappedBy = "customer": Order 엔티티의 customer 필드가 FK 주인
  // cascade = {}: 기본값(부모 저장/삭제가 자식에 전파되지 않음)
  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<Order> orders = new ArrayList<>();

  public Customer() {}

  public Long getId()                       { return id; }
  public String getName()                   { return name; }
  public void setName(String v)             { this.name = v; }
  public String getEmail()                  { return email; }
  public void setEmail(String v)            { this.email = v; }
  public String getCity()                   { return city; }
  public void setCity(String v)             { this.city = v; }
  public LocalDateTime getCreatedAt()       { return createdAt; }
  public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
  public LocalDateTime getUpdatedAt()       { return updatedAt; }
  public void setUpdatedAt(LocalDateTime v) { this.updatedAt = v; }
  public List<Order> getOrders()            { return orders; }

  // 양방향 연관관계 편의 메서드: 양쪽을 동시에 설정하여 일관성 유지
  public void addOrder(Order order) {
    orders.add(order);
    order.setCustomer(this);
  }

  @Override
  public String toString() {
    return String.format("Customer{id=%d, name='%s', city='%s'}", id, name, city);
  }
}
