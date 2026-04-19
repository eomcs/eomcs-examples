package com.eomcs.advanced.jpa.exam30;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam30 - 읽기 전용 트랜잭션 & 성능 측정
//
// @Transactional(readOnly = true) 효과 요약:
//   1. Dirty Checking 스냅샷 생성 안 함 → 엔티티 수 × 필드 수만큼 메모리 절약
//   2. flush() 자동 실행 안 함 → DB 쓰기 없음
//   3. 일부 DB/드라이버: readOnly 힌트로 읽기 최적화
//   4. 읽기/쓰기 DB 분리 아키텍처에서 읽기 DB로 자동 라우팅 가능
//
// Hibernate Statistics로 두 트랜잭션 방식의 차이를 수치로 비교한다:
//   getEntityUpdateCount() : readOnly=true는 0, 일반은 Dirty 감지 시 > 0
//   getFlushCount()        : readOnly=true는 0, 일반은 트랜잭션 종료 시 1+
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam30.App
//
public class App {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerService      svc   = ctx.getBean(CustomerService.class);
      EntityManagerFactory emf   = ctx.getBean(EntityManagerFactory.class);
      SessionFactory       sf    = emf.unwrap(SessionFactory.class);
      Statistics           stats = sf.getStatistics();

      // ── 1. readOnly=true: Dirty Checking 없음 ─────────────────────────
      System.out.println("=== 1. readOnly=true 트랜잭션 ===");
      stats.clear();

      svc.findAll().forEach(c -> System.out.println("   " + c));

      System.out.println("   [통계]");
      System.out.println("   쿼리 실행 수   : " + stats.getQueryExecutionCount());
      System.out.println("   엔티티 로드 수 : " + stats.getEntityLoadCount());
      System.out.println("   flush 횟수     : " + stats.getFlushCount());
      System.out.println("   엔티티 수정 수 : " + stats.getEntityUpdateCount());

      // ── 2. readOnly=true에서 수정 시도: DB 반영 안 됨 ─────────────────
      System.out.println("\n=== 2. readOnly=true에서 필드 수정 시도 ===");
      stats.clear();

      Customer tried = svc.findAndTryModify(1L);
      System.out.println("   메서드 반환 후 city: " + tried.getCity() + " (메모리만 변경)");

      // 실제 DB 값 확인 (다른 트랜잭션에서 재조회)
      String dbCity = svc.findAll().stream()
          .filter(c -> c.getId().equals(1L))
          .map(Customer::getCity)
          .findFirst().orElse("없음");
      System.out.println("   DB 실제 city  : " + dbCity + " (DB 변경 없음)");
      System.out.println("   flush 횟수    : " + stats.getFlushCount() + " (readOnly → flush 없음)");

      // ── 3. 일반 트랜잭션: Dirty Checking → 자동 UPDATE ────────────────
      System.out.println("\n=== 3. 일반 @Transactional - Dirty Checking → 자동 UPDATE ===");
      stats.clear();

      String originalCity = svc.findAll().stream()
          .filter(c -> c.getId().equals(1L))
          .map(Customer::getCity)
          .findFirst().orElse("");

      Customer modified = svc.findAndModify(1L, "임시도시");
      System.out.println("   수정 후 city  : " + modified.getCity());
      System.out.println("   flush 횟수    : " + stats.getFlushCount() + " (Dirty Check → flush 실행)");
      System.out.println("   엔티티 수정 수: " + stats.getEntityUpdateCount());

      // 원복
      svc.findAndModify(1L, originalCity);
      System.out.println("   원복 완료 → " + originalCity);

      System.out.println("\n[정리]");
      System.out.println("  readOnly=true  : Dirty Check 스냅샷 없음, flush 없음 → 메모리/성능 최적화");
      System.out.println("  일반 트랜잭션  : 스냅샷 생성, flush 시 변경 감지 → 자동 UPDATE 실행");
      System.out.println("  권장 패턴      : 조회 메서드에 readOnly=true, 변경 메서드에 @Transactional");
    }
  }
}
