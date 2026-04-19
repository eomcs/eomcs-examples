package com.eomcs.advanced.jpa.exam07;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

// exam07 - 기본 엔티티 매핑: Customer 엔티티
//
// @Entity  : JPA 관리 대상 클래스. 기본 생성자(public 또는 protected)가 필수다.
// @Table   : 매핑할 테이블 이름 지정. name을 생략하면 클래스명을 사용한다.
// @Id      : 기본 키 필드 지정. 엔티티에 반드시 1개 있어야 한다.
// @GeneratedValue : 기본 키 자동 생성 전략.
//   IDENTITY → DB의 IDENTITY 컬럼(Oracle: GENERATED ALWAYS AS IDENTITY)
//   SEQUENCE → DB 시퀀스 사용
//   AUTO     → Hibernate가 DB 방언에 맞는 전략 자동 선택
// @Column  : 컬럼 매핑 세부 설정.
//   name       : 실제 컬럼명 (Java 필드명과 다를 때 사용)
//   nullable   : NOT NULL 제약 (DDL 생성 시 반영, 유효성 검사에도 활용)
//   length     : VARCHAR 길이
//   precision  : NUMBER 전체 자릿수
//   scale      : NUMBER 소수점 자릿수
//   unique     : UNIQUE 제약
//
@Entity
@Table(name = "shop_customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // name이 Java 필드명과 컬럼명이 같으므로 name 생략 가능. 명시적으로 적어 가독성 향상.
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "email", nullable = false, length = 200, unique = true)
  private String email;

  @Column(name = "city", length = 100)
  private String city;

  @Column(name = "street", length = 200)
  private String street;

  @Column(name = "zipcode", length = 20)
  private String zipcode;

  // Java의 LocalDateTime ↔ Oracle의 TIMESTAMP 자동 매핑
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  // JPA는 기본 생성자가 필수다. (리플렉션으로 객체를 생성하기 때문)
  public Customer() {}

  public Long getId()                        { return id; }
  public String getName()                    { return name; }
  public void setName(String v)              { this.name = v; }
  public String getEmail()                   { return email; }
  public void setEmail(String v)             { this.email = v; }
  public String getCity()                    { return city; }
  public void setCity(String v)              { this.city = v; }
  public String getStreet()                  { return street; }
  public void setStreet(String v)            { this.street = v; }
  public String getZipcode()                 { return zipcode; }
  public void setZipcode(String v)           { this.zipcode = v; }
  public LocalDateTime getCreatedAt()        { return createdAt; }
  public void setCreatedAt(LocalDateTime v)  { this.createdAt = v; }
  public LocalDateTime getUpdatedAt()        { return updatedAt; }
  public void setUpdatedAt(LocalDateTime v)  { this.updatedAt = v; }

  @Override
  public String toString() {
    return String.format(
        "Customer{id=%d, name='%s', email='%s', city='%s', street='%s', zipcode='%s'}",
        id, name, email, city, street, zipcode);
  }
}
