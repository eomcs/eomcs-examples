package com.eomcs.advanced.jpa.exam16;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

// exam16 - @EntityListeners로 Auditing 구현
//
// @EntityListeners(AuditListener.class):
//   - 엔티티 생명주기 이벤트 발생 시 AuditListener 메서드가 자동 호출된다.
//   - createdAt / updatedAt 을 애플리케이션 코드에서 직접 설정하지 않아도 된다.
//
// 이벤트 어노테이션을 엔티티 내부 메서드에 직접 붙이는 방법도 있다:
//   @PrePersist void onPrePersist() { ... }
//   단, 외부 리스너 방식이 여러 엔티티에 공통 적용하기 더 편리하다.
//
@Entity
@Table(name = "shop_customer")
@EntityListeners(AuditListener.class)
public class Customer implements Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 200)
  private String email;

  @Column(length = 100)
  private String city;

  // AuditListener의 @PrePersist에서 자동 설정된다.
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  // AuditListener의 @PrePersist / @PreUpdate에서 자동 갱신된다.
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Customer() {}

  public Long getId()                           { return id; }
  public String getName()                       { return name; }
  public void setName(String v)                 { this.name = v; }
  public String getEmail()                      { return email; }
  public void setEmail(String v)                { this.email = v; }
  public String getCity()                       { return city; }
  public void setCity(String v)                 { this.city = v; }
  public LocalDateTime getCreatedAt()           { return createdAt; }

  @Override
  public void setCreatedAt(LocalDateTime v)     { this.createdAt = v; }

  public LocalDateTime getUpdatedAt()           { return updatedAt; }

  @Override
  public void setUpdatedAt(LocalDateTime v)     { this.updatedAt = v; }

  @Override
  public String toString() {
    return String.format(
        "Customer{id=%d, name='%s', createdAt=%s, updatedAt=%s}",
        id, name, createdAt, updatedAt);
  }
}
