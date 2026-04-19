package com.eomcs.advanced.jpa.exam17;

// exam17 - JPQL 생성자 표현식(Constructor Expression)용 DTO
//
// JPQL: SELECT new com.eomcs.advanced.jpa.exam17.CustomerNameCityDto(c.name, c.city)
//        FROM Customer c
//
// - 전체 엔티티 대신 필요한 필드만 담는 경량 DTO 반환
// - new 키워드와 FQCN(완전 클래스명)을 사용하며, 일치하는 생성자가 반드시 존재해야 한다
//
public class CustomerNameCityDto {

  private final String name;
  private final String city;

  public CustomerNameCityDto(String name, String city) {
    this.name = name;
    this.city = city;
  }

  public String getName() { return name; }
  public String getCity() { return city; }

  @Override
  public String toString() {
    return String.format("CustomerNameCityDto{name='%s', city='%s'}", name, city);
  }
}
