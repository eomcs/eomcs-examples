package com.eomcs.advanced.jpa.exam20;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam20 - Native Query: 네이티브 SQL 사용
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam20.App1
//
public class App1 {

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
        Persistence.createEntityManagerFactory("exam20", props)) {

      // ── 1. 기본 네이티브 쿼리 (엔티티 반환) ──────────────────────────────────
      System.out.println("=== 1. 기본 네이티브 쿼리: 엔티티 반환 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Customer> customers = em.createNativeQuery(
            "SELECT * FROM shop_customer ORDER BY id",
            Customer.class)
            .getResultList();

        customers.forEach(c -> System.out.println("  " + c));
        // createNativeQuery(sql, 엔티티클래스): 결과를 지정한 엔티티로 매핑
        // JPA가 컬럼명 ↔ 필드명(@Column(name=...))을 매핑하여 엔티티 반환
      }

      // ── 2. 이름 기반 파라미터 바인딩 ─────────────────────────────────────────
      System.out.println("\n=== 2. 이름 기반 파라미터: 도시 필터 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Customer> result = em.createNativeQuery(
            "SELECT * FROM shop_customer WHERE city = :city ORDER BY id",
            Customer.class)
            .setParameter("city", "서울")
            .getResultList();

        result.forEach(c -> System.out.println("  " + c));
      }

      // ── 3. 위치 기반 파라미터 ─────────────────────────────────────────────────
      System.out.println("\n=== 3. 위치 기반 파라미터: ?1 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Customer> result = em.createNativeQuery(
            "SELECT * FROM shop_customer WHERE city = ?1 ORDER BY id",
            Customer.class)
            .setParameter(1, "부산")
            .getResultList();

        result.forEach(c -> System.out.println("  " + c));
      }

      // ── 4. Object[] 반환 (엔티티 없이 집계 결과 조회) ──────────────────────
      System.out.println("\n=== 4. Object[] 반환: 도시별 고객 수 집계 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(
            "SELECT city, COUNT(*) AS cnt"
            + " FROM shop_customer"
            + " WHERE city IS NOT NULL"
            + " GROUP BY city"
            + " ORDER BY cnt DESC")
            .getResultList();

        rows.forEach(row ->
            System.out.printf("  도시: %-6s | 고객 수: %s%n", row[0], row[1]));
        // 엔티티 클래스를 지정하지 않으면 결과를 Object[] 로 반환한다.
      }

      // ── 5. 조인을 포함한 네이티브 SQL ─────────────────────────────────────────
      System.out.println("\n=== 5. 네이티브 JOIN: 고객별 주문 수 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(
            "SELECT c.name, COUNT(o.id) AS order_count"
            + " FROM shop_customer c"
            + " LEFT JOIN shop_orders o ON o.customer_id = c.id"
            + " GROUP BY c.id, c.name"
            + " ORDER BY order_count DESC, c.id")
            .getResultList();

        rows.forEach(row ->
            System.out.printf("  %-8s | 주문 수: %s건%n", row[0], row[1]));
      }

      // ── 6. Oracle 전용 함수 활용 ──────────────────────────────────────────────
      System.out.println("\n=== 6. Oracle 전용 함수: REGEXP_LIKE, LISTAGG ===");
      try (EntityManager em = emf.createEntityManager()) {
        // JPQL로는 표현하기 어려운 Oracle 전용 문법 사용 가능
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(
            "SELECT id, name, email"
            + " FROM shop_customer"
            + " WHERE REGEXP_LIKE(email, '^[a-z]+@example\\.com$')"
            + " ORDER BY id")
            .getResultList();

        rows.forEach(row ->
            System.out.printf("  id=%-3s | name=%-6s | email=%s%n",
                row[0], row[1], row[2]));
        // Native Query 장점: DB 고유 함수, 힌트, 복잡한 SQL 표현 자유롭게 사용
      }

      System.out.println("\n[정리: Native Query]");
      System.out.println("  createNativeQuery(sql, 엔티티.class) → 엔티티로 자동 매핑");
      System.out.println("  createNativeQuery(sql)               → Object[] 배열 반환");
      System.out.println("  파라미터 바인딩 :name / ?1           → JPQL과 동일");
      System.out.println("  장점           : DB 고유 문법·함수·힌트 자유 사용");
      System.out.println("  단점           : DB 종속, JPQL 이식성 없음, 컴파일 검증 불가");
    }
  }
}
