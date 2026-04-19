package com.eomcs.advanced.jpa.exam16;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

// exam16 - 엔티티 이벤트 리스너
//
// @EntityListeners(AuditListener.class)로 등록하면
// Hibernate가 엔티티 생명주기 이벤트 발생 시 이 클래스의 메서드를 호출한다.
//
// 이벤트 호출 순서:
//   INSERT: @PrePersist → INSERT SQL → @PostPersist
//   UPDATE: @PreUpdate  → UPDATE SQL → @PostUpdate
//   DELETE: @PreRemove  → DELETE SQL → @PostRemove
//   SELECT: @PostLoad (em.find() 또는 JPQL 조회 후)
//
// @PrePersist 활용:
//   - createdAt, updatedAt 자동 설정 → 애플리케이션 코드에서 직접 설정 불필요
//
// @PreUpdate 활용:
//   - updatedAt 자동 갱신 → 변경 시마다 수동으로 setUpdatedAt() 불필요
//
public class AuditListener {

  @PrePersist
  public void prePersist(Object entity) {
    if (entity instanceof Auditable auditable) {
      java.time.LocalDateTime now = java.time.LocalDateTime.now();
      auditable.setCreatedAt(now);
      auditable.setUpdatedAt(now);
      System.out.println("  [AuditListener @PrePersist] createdAt/updatedAt 자동 설정: " + now);
    }
  }

  @PostPersist
  public void postPersist(Object entity) {
    System.out.println("  [AuditListener @PostPersist] INSERT 완료: " + entity);
  }

  @PreUpdate
  public void preUpdate(Object entity) {
    if (entity instanceof Auditable auditable) {
      java.time.LocalDateTime now = java.time.LocalDateTime.now();
      auditable.setUpdatedAt(now);
      System.out.println("  [AuditListener @PreUpdate] updatedAt 자동 갱신: " + now);
    }
  }

  @PostUpdate
  public void postUpdate(Object entity) {
    System.out.println("  [AuditListener @PostUpdate] UPDATE 완료: " + entity);
  }

  @PreRemove
  public void preRemove(Object entity) {
    System.out.println("  [AuditListener @PreRemove] DELETE 예정: " + entity);
  }

  @PostRemove
  public void postRemove(Object entity) {
    System.out.println("  [AuditListener @PostRemove] DELETE 완료: " + entity);
  }

  @PostLoad
  public void postLoad(Object entity) {
    System.out.println("  [AuditListener @PostLoad] 엔티티 로딩 완료: " + entity);
  }
}
