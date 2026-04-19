package com.eomcs.advanced.jpa.exam20;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

// exam20 - Native Query & @SqlResultSetMapping 고객 엔티티
//
// @SqlResultSetMapping: 네이티브 쿼리 결과 컬럼을 DTO 생성자 인수로 매핑
// @NamedNativeQuery: 재사용 가능한 Named 네이티브 쿼리 정의
//
@Entity
@Table(name = "shop_customer")
@SqlResultSetMapping(
    name    = "CustomerSummaryMapping",
    classes = @ConstructorResult(
        targetClass = CustomerSummary.class,
        columns     = {
            @ColumnResult(name = "id",          type = Long.class),
            @ColumnResult(name = "name",         type = String.class),
            @ColumnResult(name = "city",         type = String.class),
            @ColumnResult(name = "order_count",  type = Long.class)
        }
    )
)
@NamedNativeQuery(
    name            = "Customer.findSummary",
    query           = """
        SELECT c.id, c.name, c.city,
               (SELECT COUNT(*) FROM shop_orders o WHERE o.customer_id = c.id) AS order_count
          FROM shop_customer c
         ORDER BY c.id
        """,
    resultSetMapping = "CustomerSummaryMapping"
)
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

  @Override
  public String toString() {
    return String.format("Customer{id=%d, name='%s', city='%s'}",
        id, name, city);
  }
}
