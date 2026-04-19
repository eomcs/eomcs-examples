package com.eomcs.advanced.jpa.exam25;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam25 - Auditing & @EnableJpaAuditing
//
// JPA Auditing: 엔티티 생성/수정 시 타임스탬프와 사용자 정보를 자동으로 채운다.
//   개발자가 setCreatedAt(LocalDateTime.now()) 같은 코드를 작성할 필요가 없다.
//
// 동작 흐름:
//   save(entity) → @PrePersist 이벤트 → AuditingEntityListener
//     → @CreatedDate  : LocalDateTime.now() 자동 설정
//     → @CreatedBy    : AuditorAware.getCurrentAuditor() 반환값 자동 설정
//   save(entity) → @PreUpdate 이벤트 → AuditingEntityListener
//     → @LastModifiedDate : LocalDateTime.now() 자동 갱신
//     → @LastModifiedBy   : AuditorAware.getCurrentAuditor() 반환값 자동 갱신
//
// 이 예제에서 확인할 내용:
//   1. INSERT 시 createdAt, updatedAt, createdBy, updatedBy 자동 설정
//   2. UPDATE 시 updatedAt, updatedBy 자동 갱신 (createdAt, createdBy 불변)
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam25.App
//
public class App {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository repo = ctx.getBean(CustomerRepository.class);

      // ── 1. INSERT - Auditing 자동 설정 ─────────────────────────────────
      System.out.println("=== 1. INSERT - @CreatedDate / @CreatedBy 자동 설정 ===");
      Customer c = new Customer();
      c.setName("감사테스터");
      // createdAt, updatedAt, createdBy, updatedBy 는 설정하지 않아도 자동 채워짐
      c.setEmail("audit_" + System.currentTimeMillis() + "@test.com");
      c.setCity("서울");

      Customer saved = repo.save(c);
      System.out.println("  저장 후:");
      System.out.println("    createdAt  : " + saved.getCreatedAt());
      System.out.println("    updatedAt  : " + saved.getUpdatedAt());
      System.out.println("    createdBy  : " + saved.getCreatedBy());
      System.out.println("    updatedBy  : " + saved.getUpdatedBy());

      // ── 2. UPDATE - @LastModifiedDate / @LastModifiedBy 자동 갱신 ──────
      System.out.println("\n=== 2. UPDATE - @LastModifiedDate / @LastModifiedBy 자동 갱신 ===");

      // 잠시 대기 후 수정 (updatedAt 변화 확인)
      try { Thread.sleep(10); } catch (InterruptedException ignored) {}

      Customer toUpdate = repo.findById(saved.getId()).orElseThrow();
      toUpdate.setCity("부산");
      Customer updated = repo.save(toUpdate);

      System.out.println("  수정 후:");
      System.out.println("    createdAt  : " + updated.getCreatedAt() + " (불변)");
      System.out.println("    updatedAt  : " + updated.getUpdatedAt() + " (갱신됨)");
      System.out.println("    createdBy  : " + updated.getCreatedBy() + " (불변)");
      System.out.println("    updatedBy  : " + updated.getUpdatedBy() + " (갱신됨)");
      System.out.println("    createdAt == updatedAt: "
          + updated.getCreatedAt().equals(updated.getUpdatedAt()));

      System.out.println("\n[정리]");
      System.out.println("  @EnableJpaAuditing           : Auditing 기능 활성화");
      System.out.println("  @EntityListeners(Auditing..) : 엔티티 이벤트를 Auditing 리스너에 연결");
      System.out.println("  @CreatedDate                 : persist 시 현재 시각 자동 설정 (updatable=false)");
      System.out.println("  @LastModifiedDate            : persist/merge 시 현재 시각 자동 갱신");
      System.out.println("  @CreatedBy                   : persist 시 AuditorAware 값 자동 설정");
      System.out.println("  @LastModifiedBy              : persist/merge 시 AuditorAware 값 자동 갱신");
    }
  }
}
