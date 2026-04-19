package com.eomcs.advanced.jpa.exam06;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

// exam06용 Customer 엔티티 - JPA 소개를 위한 최소한의 매핑
//
// @Entity : 이 클래스가 JPA 엔티티임을 선언한다.
//           Hibernate는 이 클래스를 테이블과 매핑 대상으로 관리한다.
// @Table  : 매핑할 테이블 이름을 명시한다. 생략하면 클래스 이름을 사용한다.
// @Id     : 기본 키(Primary Key) 컬럼을 지정한다.
// @GeneratedValue : 기본 키 자동 생성 전략을 지정한다.
//           GenerationType.IDENTITY → DB의 IDENTITY/AUTO_INCREMENT 기능을 사용한다.
//           Oracle: GENERATED ALWAYS AS IDENTITY 컬럼에 대응한다.
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

  public Long getId()             { return id; }
  public String getName()         { return name; }
  public void setName(String v)   { this.name = v; }
  public String getEmail()        { return email; }
  public void setEmail(String v)  { this.email = v; }
  public String getCity()         { return city; }
  public void setCity(String v)   { this.city = v; }
  public LocalDateTime getCreatedAt()          { return createdAt; }
  public void setCreatedAt(LocalDateTime v)    { this.createdAt = v; }
  public LocalDateTime getUpdatedAt()          { return updatedAt; }
  public void setUpdatedAt(LocalDateTime v)    { this.updatedAt = v; }

  @Override
  public String toString() {
    return String.format("Customer{id=%d, name='%s', email='%s', city='%s'}",
        id, name, email, city);
  }
}
