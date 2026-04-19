package com.eomcs.advanced.jpa.exam12;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// exam12 - 값 타입(Value Type): @Embeddable
//
// @Embeddable:
//   - 독립적인 생명주기가 없는 값 타입을 정의한다.
//   - 자체 @Id가 없으며 소유 엔티티(Customer)의 테이블에 컬럼으로 매핑된다.
//   - 소유 엔티티가 삭제되면 값 타입도 함께 사라진다.
//
// 값 타입 vs 엔티티 타입:
//   - 엔티티: 식별자(@Id)가 있고 독립적 생명주기를 가짐
//   - 값 타입: 식별자 없고 소유 엔티티에 종속
//
// shop_customer 테이블의 city, street, zipcode 컬럼을 하나의 값 타입으로 묶는다.
//
@Embeddable
public class Address {

  @Column(length = 100)
  private String city;

  @Column(length = 200)
  private String street;

  @Column(length = 20)
  private String zipcode;

  public Address() {}

  public Address(String city, String street, String zipcode) {
    this.city    = city;
    this.street  = street;
    this.zipcode = zipcode;
  }

  public String getCity()           { return city; }
  public void setCity(String v)     { this.city = v; }
  public String getStreet()         { return street; }
  public void setStreet(String v)   { this.street = v; }
  public String getZipcode()        { return zipcode; }
  public void setZipcode(String v)  { this.zipcode = v; }

  @Override
  public String toString() {
    return String.format("Address{city='%s', street='%s', zipcode='%s'}", city, street, zipcode);
  }
}
