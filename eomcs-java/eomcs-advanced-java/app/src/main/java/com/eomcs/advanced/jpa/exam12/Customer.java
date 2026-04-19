package com.eomcs.advanced.jpa.exam12;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

// exam12 - @Embedded: Customer가 Address 값 타입을 포함
//
// @Embedded:
//   - @Embeddable로 정의된 값 타입 필드에 붙인다.
//   - 별도 테이블 없이 소유 엔티티(shop_customer) 테이블의 컬럼으로 저장된다.
//   - city, street, zipcode 컬럼이 Customer 테이블에 그대로 존재한다.
//
// @AttributeOverride (이 예제에서는 미사용):
//   - 같은 @Embeddable을 여러 번 사용할 때 컬럼명을 재정의할 수 있다.
//   - 예: homeAddress.city → home_city, workAddress.city → work_city
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

  // city, street, zipcode 컬럼을 Address 값 타입으로 묶어 관리
  @Embedded
  private Address address;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Customer() {}

  public Long getId()                           { return id; }
  public String getName()                       { return name; }
  public void setName(String v)                 { this.name = v; }
  public String getEmail()                      { return email; }
  public void setEmail(String v)                { this.email = v; }
  public Address getAddress()                   { return address; }
  public void setAddress(Address v)             { this.address = v; }
  public LocalDateTime getCreatedAt()           { return createdAt; }
  public void setCreatedAt(LocalDateTime v)     { this.createdAt = v; }
  public LocalDateTime getUpdatedAt()           { return updatedAt; }
  public void setUpdatedAt(LocalDateTime v)     { this.updatedAt = v; }

  @Override
  public String toString() {
    return String.format("Customer{id=%d, name='%s', address=%s}", id, name, address);
  }
}
