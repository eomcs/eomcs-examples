package com.eomcs.advanced.jpa.exam20;

// exam20 - @SqlResultSetMapping / ConstructorResult 매핑용 DTO
//
// @ConstructorResult 를 사용하면 네이티브 쿼리 결과를 엔티티 없이 이 생성자로 직접 매핑한다.
// 생성자 인수 타입이 @ColumnResult(type=...) 과 일치해야 한다.
//
public class CustomerSummary {

  private final Long   id;
  private final String name;
  private final String city;
  private final Long   orderCount;

  public CustomerSummary(Long id, String name, String city, Long orderCount) {
    this.id         = id;
    this.name       = name;
    this.city       = city;
    this.orderCount = orderCount;
  }

  public Long   getId()         { return id; }
  public String getName()       { return name; }
  public String getCity()       { return city; }
  public Long   getOrderCount() { return orderCount; }

  @Override
  public String toString() {
    return String.format("CustomerSummary{id=%d, name='%s', city='%s', orderCount=%d}",
        id, name, city, orderCount);
  }
}
