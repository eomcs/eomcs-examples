package com.eomcs.advanced.jpa.exam28;

import java.util.List;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// exam28 - 2차 캐시 (Ehcache 3 + JCache)
//
// 1차 캐시(L1C): EntityManager 단위 - 트랜잭션 종료 시 소멸
// 2차 캐시(L2C): EntityManagerFactory(SessionFactory) 단위 - 애플리케이션 전체 공유
//
// 동작 확인:
//   findById(1L) 첫 번째 → DB SELECT → L2C에 저장
//   findById(1L) 두 번째 → L2C에서 조회 (DB 쿼리 없음) → 캐시 히트
//   Statistics.getSecondLevelCacheHitCount() : L2C 히트 횟수
//   Statistics.getSecondLevelCacheMissCount(): L2C 미스 횟수
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam28.App
//
public class App {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(JpaConfig.class)) {

      CustomerRepository repo    = ctx.getBean(CustomerRepository.class);
      EntityManagerFactory emf   = ctx.getBean(EntityManagerFactory.class);
      SessionFactory       sf    = emf.unwrap(SessionFactory.class);
      Statistics           stats = sf.getStatistics();

      stats.clear();
      System.out.println("=== 엔티티 2차 캐시 (L2C) 데모 ===\n");

      // ── 1. 첫 번째 조회: L2C 미스 → DB 쿼리 실행 ─────────────────────
      System.out.println(">> [1차 조회] findById(1L) - DB SELECT 실행, L2C에 저장");
      repo.findById(1L).ifPresent(c -> System.out.println("   " + c));
      printStats(stats, "1차 조회 후");

      // ── 2. 두 번째 조회: L2C 히트 → DB 쿼리 없음 ─────────────────────
      System.out.println("\n>> [2차 조회] findById(1L) - L2C 히트, DB 쿼리 없음");
      repo.findById(1L).ifPresent(c -> System.out.println("   " + c));
      printStats(stats, "2차 조회 후");

      // ── 3. 다른 ID 조회: L2C 미스 → DB 쿼리 실행 ─────────────────────
      System.out.println("\n>> [3차 조회] findById(2L) - L2C 미스, DB SELECT 실행");
      repo.findById(2L).ifPresent(c -> System.out.println("   " + c));
      printStats(stats, "3차 조회 후");

      // ── 4. 쿼리 캐시 ──────────────────────────────────────────────────
      stats.clear();
      System.out.println("\n=== 쿼리 캐시 데모 ===\n");
      System.out.println(">> [1차 쿼리] findByCityWithCache(\"서울\") - DB 실행");
      List<Customer> r1 = repo.findByCityWithCache("서울");
      System.out.println("   결과: " + r1.size() + "건");
      System.out.println("   쿼리 실행 횟수: " + stats.getQueryExecutionCount());

      System.out.println("\n>> [2차 쿼리] findByCityWithCache(\"서울\") - 쿼리 캐시 히트");
      List<Customer> r2 = repo.findByCityWithCache("서울");
      System.out.println("   결과: " + r2.size() + "건");
      System.out.println("   쿼리 실행 횟수: " + stats.getQueryExecutionCount() + " (변화 없음)");

      System.out.println("\n[정리]");
      System.out.println("  1차 캐시(L1C): EntityManager 범위, 트랜잭션 종료 시 소멸");
      System.out.println("  2차 캐시(L2C): SessionFactory 범위, 애플리케이션 전체 공유");
      System.out.println("  @Cache       : 엔티티를 L2C 대상으로 표시");
      System.out.println("  쿼리 캐시    : @QueryHints로 쿼리 결과도 캐싱 가능");
      System.out.println("  주의         : 자주 변경되는 데이터에는 캐시 효과 적음");
    }
  }

  private static void printStats(Statistics stats, String label) {
    System.out.printf("   [%s] L2C 히트: %d, 미스: %d, PUT: %d%n",
        label,
        stats.getSecondLevelCacheHitCount(),
        stats.getSecondLevelCacheMissCount(),
        stats.getSecondLevelCachePutCount());
  }
}
