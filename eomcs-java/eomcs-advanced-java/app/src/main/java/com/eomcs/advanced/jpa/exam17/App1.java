package com.eomcs.advanced.jpa.exam17;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam17 - JPQL 기초: 기본 쿼리 문법
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam17.App1
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
        Persistence.createEntityManagerFactory("exam17", props)) {

      // ── 1. 전체 조회 (SELECT ... FROM) ─────────────────────────────────────
      System.out.println("=== 1. 전체 고객 조회 (SELECT c FROM Customer c) ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> customers = em.createQuery(
            "SELECT c FROM Customer c ORDER BY c.id",
            Customer.class)
            .getResultList();

        customers.forEach(c -> System.out.println("  " + c));
        // JPQL은 테이블명 대신 엔티티명(Customer), 컬럼명 대신 필드명(c.id)을 사용한다.
      }

      // ── 2. WHERE: 특정 도시 고객 ────────────────────────────────────────────
      System.out.println("\n=== 2. WHERE: 서울 고객 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> result = em.createQuery(
            "SELECT c FROM Customer c WHERE c.city = '서울' ORDER BY c.id",
            Customer.class)
            .getResultList();

        result.forEach(c -> System.out.println("  " + c));
      }

      // ── 3. LIKE: 이메일 도메인 필터 ─────────────────────────────────────────
      System.out.println("\n=== 3. LIKE: @example.com 이메일 고객 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> result = em.createQuery(
            "SELECT c FROM Customer c WHERE c.email LIKE '%@example.com' ORDER BY c.id",
            Customer.class)
            .getResultList();

        result.forEach(c -> System.out.println("  " + c));
      }

      // ── 4. BETWEEN: 가격 범위 조회 ──────────────────────────────────────────
      System.out.println("\n=== 4. BETWEEN: 100,000 ~ 2,000,000원 제품 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Product> result = em.createQuery(
            "SELECT p FROM Product p"
            + " WHERE p.price BETWEEN :low AND :high ORDER BY p.price",
            Product.class)
            .setParameter("low",  new BigDecimal("100000"))
            .setParameter("high", new BigDecimal("2000000"))
            .getResultList();

        result.forEach(p -> System.out.println("  " + p));
      }

      // ── 5. IN: 특정 값 목록 ─────────────────────────────────────────────────
      System.out.println("\n=== 5. IN: 서울/부산 고객 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> result = em.createQuery(
            "SELECT c FROM Customer c WHERE c.city IN ('서울', '부산') ORDER BY c.id",
            Customer.class)
            .getResultList();

        result.forEach(c -> System.out.println("  " + c));
      }

      // ── 6. IS NULL / IS NOT NULL ─────────────────────────────────────────────
      System.out.println("\n=== 6. IS NOT NULL: 도시 정보가 있는 고객 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> result = em.createQuery(
            "SELECT c FROM Customer c WHERE c.city IS NOT NULL ORDER BY c.id",
            Customer.class)
            .getResultList();

        result.forEach(c -> System.out.println("  " + c));
      }

      // ── 7. 집계 함수 (COUNT, SUM, AVG, MIN, MAX) ────────────────────────────
      System.out.println("\n=== 7. 집계 함수: 제품 통계 ===");
      try (EntityManager em = emf.createEntityManager()) {
        Object[] stats = (Object[]) em.createQuery(
            "SELECT COUNT(p), AVG(p.price), MIN(p.price), MAX(p.price) FROM Product p")
            .getSingleResult();

        System.out.printf("  총 제품 수: %s개%n",  stats[0]);
        System.out.printf("  평균 가격:  %.2f원%n", stats[1]);
        System.out.printf("  최저 가격:  %s원%n",   stats[2]);
        System.out.printf("  최고 가격:  %s원%n",   stats[3]);
      }

      // ── 8. GROUP BY + HAVING ─────────────────────────────────────────────────
      System.out.println("\n=== 8. GROUP BY + HAVING: 도시별 고객 수 ===");
      try (EntityManager em = emf.createEntityManager()) {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createQuery(
            "SELECT c.city, COUNT(c)"
            + " FROM Customer c"
            + " WHERE c.city IS NOT NULL"
            + " GROUP BY c.city"
            + " HAVING COUNT(c) >= 1"
            + " ORDER BY COUNT(c) DESC")
            .getResultList();

        rows.forEach(row ->
            System.out.printf("  도시: %-6s | 고객 수: %s%n", row[0], row[1]));
      }

      // ── 9. ORDER BY 다중 정렬 ────────────────────────────────────────────────
      System.out.println("\n=== 9. ORDER BY 다중 정렬: 도시 ASC, 이름 DESC ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> result = em.createQuery(
            "SELECT c FROM Customer c ORDER BY c.city ASC, c.name DESC",
            Customer.class)
            .getResultList();

        result.forEach(c -> System.out.printf("  [%-4s] %s%n", c.getCity(), c.getName()));
      }

      System.out.println("\n[정리: JPQL 기본 문법]");
      System.out.println("  SELECT c FROM Customer c    → 엔티티명, 필드명 사용 (테이블명·컬럼명 X)");
      System.out.println("  WHERE / LIKE / BETWEEN / IN → SQL과 동일한 조건 연산자");
      System.out.println("  IS NULL / IS NOT NULL       → null 비교");
      System.out.println("  COUNT·SUM·AVG·MIN·MAX       → 집계 함수");
      System.out.println("  GROUP BY / HAVING           → 그룹화 및 그룹 조건");
    }
  }
}
