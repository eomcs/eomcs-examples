package com.eomcs.advanced.jpa.exam25;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// exam25: shop_customer 테이블에는 created_by / updated_by 컬럼이 없어,
//   exam25_customer 테이블을 별도로 사용한다.
//   JpaConfig에서 hbm2ddl.auto=create-drop으로 실행 시 자동 생성/삭제된다.
@Entity
@Table(name = "exam25_customer")
public class Customer extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "email", nullable = false, length = 200, unique = true)
  private String email;

  @Column(name = "city", length = 100)
  private String city;

  public Customer() {}

  public Long getId()          { return id; }
  public String getName()      { return name; }
  public void setName(String v)  { this.name = v; }
  public String getEmail()     { return email; }
  public void setEmail(String v) { this.email = v; }
  public String getCity()      { return city; }
  public void setCity(String v)  { this.city = v; }

  @Override
  public String toString() {
    return String.format(
        "Customer{id=%d, name='%s', city='%s', createdAt=%s, updatedAt=%s, createdBy='%s', updatedBy='%s'}",
        id, name, city, getCreatedAt(), getUpdatedAt(), getCreatedBy(), getUpdatedBy());
  }
}
