package com.eomcs.advanced.jpa.exam08;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

// exam08 - 영속성 컨텍스트: 1차 캐시 & 동일성 보장
//
// 영속성 컨텍스트(Persistence Context):
// - EntityManager 내부에 존재하는 "엔티티 저장소"이다.
// - 영속 상태(managed)의 엔티티를 Map<@Id, Entity>로 관리한다.
//
// 1차 캐시(First-Level Cache):
// - 같은 EntityManager에서 같은 id로 find()를 두 번 호출하면
//   두 번째는 DB에 접근하지 않고 1차 캐시에서 반환한다.
// - SELECT SQL이 한 번만 실행되는 것을 show_sql 로그로 확인한다.
//
// 동일성(Identity) 보장:
// - 같은 EntityManager에서 같은 id로 조회하면 항상 동일한 인스턴스(==)를 반환한다.
// - JDBC ResultSet에서 수동 매핑하면 매번 새 객체가 생성되는 것과 대조된다.
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam08.App
//
public class App {

  public static void main(String[] args) {
    String host    = System.getenv("DB_HOSTNAME");
    String port    = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");
    String url     = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;

    Map<String, String> props = new HashMap<>();
    props.put("jakarta.persistence.jdbc.url",      url);
    props.put("jakarta.persistence.jdbc.user",     System.getenv("DB_USERNAME"));
    props.put("jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));

    try (EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("exam08", props)) {

      // ── 1차 캐시: 같은 id 두 번 조회 → SQL은 한 번만 ─────────────────────
      System.out.println("=== [1차 캐시] 같은 id로 find() 두 번 호출 ===");
      System.out.println("  (show_sql=true 이므로 SELECT 실행 횟수를 확인하세요)");
      System.out.println();

      try (EntityManager em = emf.createEntityManager()) {

        System.out.println("  [1] em.find(Customer.class, 1L) 호출:");
        Customer c1 = em.find(Customer.class, 1L);
        // → 1차 캐시 미스 → DB SELECT 실행 → 캐시에 저장
        System.out.println("      결과: " + c1);

        System.out.println();
        System.out.println("  [2] em.find(Customer.class, 1L) 다시 호출:");
        Customer c2 = em.find(Customer.class, 1L);
        // → 1차 캐시 히트 → SELECT 실행 안 함!
        System.out.println("      결과: " + c2);

        System.out.println();
        System.out.println("  SELECT SQL이 몇 번 실행됐나요? → 위 로그를 확인하세요.");
        System.out.println("  정답: 1번 (두 번째 find는 캐시에서 반환)");

        // ── 동일성 보장 ───────────────────────────────────────────────────
        System.out.println();
        System.out.println("=== [동일성 보장] c1 == c2 ? ===");
        System.out.println("  c1 == c2 : " + (c1 == c2));       // true
        System.out.println("  c1.equals(c2): " + c1.equals(c2)); // true (같은 인스턴스)
        System.out.println("  c1 주소: " + System.identityHashCode(c1));
        System.out.println("  c2 주소: " + System.identityHashCode(c2));
        System.out.println();
        System.out.println("  → 같은 EntityManager에서 같은 id 조회 시 항상 동일 인스턴스");
        System.out.println("  → JDBC 수동 매핑은 find 때마다 new Customer() → 다른 인스턴스");
      }

      // ── 다른 EntityManager에서는 별도 캐시 ────────────────────────────────
      System.out.println();
      System.out.println("=== [EM 분리] 다른 EntityManager에서 조회 ===");
      System.out.println("  (새 EntityManager = 새 영속성 컨텍스트 = 1차 캐시 초기화)");

      Customer fromEm1;
      try (EntityManager em1 = emf.createEntityManager()) {
        fromEm1 = em1.find(Customer.class, 1L);
        System.out.println("  em1에서 조회: " + fromEm1);
      }
      // em1.close() → em1의 영속성 컨텍스트 소멸. fromEm1은 준영속(detached) 상태가 됨.

      try (EntityManager em2 = emf.createEntityManager()) {
        Customer fromEm2 = em2.find(Customer.class, 1L);
        // em2는 새 영속성 컨텍스트 → em1의 캐시와 무관 → DB 재조회
        System.out.println("  em2에서 조회: " + fromEm2);
        System.out.println();
        System.out.println("  fromEm1 == fromEm2 : " + (fromEm1 == fromEm2));  // false
        System.out.println("  → 다른 EntityManager에서는 다른 인스턴스");
      }

      System.out.println("\n[정리]");
      System.out.println("  1차 캐시: 같은 EM에서 같은 id → SQL 1번만 실행");
      System.out.println("  동일성 보장: 같은 EM에서 같은 id → == 비교 true");
      System.out.println("  1차 캐시 범위: EntityManager 인스턴스 = 트랜잭션/요청 단위");
      System.out.println("  EM이 닫히면 캐시 소멸 → 다른 EM은 별도 캐시 사용");
    }
  }
}
