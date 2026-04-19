package com.eomcs.advanced.jpa.exam16;

import java.time.LocalDateTime;

// exam16 - Auditing 대상 엔티티가 구현해야 할 인터페이스
//
// AuditListener가 임의의 엔티티 타입(Object)을 받으므로,
// createdAt/updatedAt 필드에 접근하려면 공통 타입이 필요하다.
//
public interface Auditable {
  void setCreatedAt(LocalDateTime v);
  void setUpdatedAt(LocalDateTime v);
}
