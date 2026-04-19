package com.eomcs.advanced.jpa.exam25;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// exam25 - Auditing 기본 추상 클래스
//
// @MappedSuperclass: DB 테이블과 매핑되지 않지만 자식 엔티티에 컬럼 매핑 정보를 상속시킨다.
//
// @EntityListeners(AuditingEntityListener.class):
//   - JPA 이벤트(@PrePersist, @PreUpdate)를 가로채 Auditing 필드를 자동 채운다.
//
// @CreatedDate    : persist 시 현재 시각을 자동 설정
// @LastModifiedDate: persist/merge 시 현재 시각을 자동 갱신
// @CreatedBy      : persist 시 AuditorAware.getCurrentAuditor() 반환값을 자동 설정
// @LastModifiedBy : persist/merge 시 AuditorAware.getCurrentAuditor() 반환값을 자동 갱신
//
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @CreatedBy
  @Column(name = "created_by", updatable = false, length = 100)
  private String createdBy;

  @LastModifiedBy
  @Column(name = "updated_by", length = 100)
  private String updatedBy;

  public LocalDateTime getCreatedAt()  { return createdAt; }
  public LocalDateTime getUpdatedAt()  { return updatedAt; }
  public String getCreatedBy()         { return createdBy; }
  public String getUpdatedBy()         { return updatedBy; }
}
