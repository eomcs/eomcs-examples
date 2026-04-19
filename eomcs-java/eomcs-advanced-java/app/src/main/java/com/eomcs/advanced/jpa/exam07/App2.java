package com.eomcs.advanced.jpa.exam07;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam07 - 기본 엔티티 매핑: @Column 세부 속성 & JPQL 기초
//
// 이 예제에서 확인할 내용:
//   1. @Column의 name 속성: Java 필드명 ≠ DB 컬럼명인 경우 명시적으로 지정한다.
//   2. JPQL의 엔티티명/필드명 규칙: DB 테이블명/컬럼명이 아님에 주의한다.
//   3. :파라미터명 바인딩: SQL Injection 방지 (JDBC의 ? 바인딩과 동일한 목적).
//   4. TypedQuery: 반환 타입을 컴파일 시점에 확인한다.
//
// JPQL vs SQL 대응 관계:
//   JPQL : SELECT c FROM Customer c WHERE c.city = :city
//   SQL  : SELECT id, name, ... FROM shop_customer WHERE city = ?
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam07.App2
//
public class App2 {

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
        Persistence.createEntityManagerFactory("exam07", props)) {

      try (EntityManager em = emf.createEntityManager()) {

        // ── 1. 전체 Customer 조회 ──────────────────────────────────────────
        System.out.println("=== 1. 전체 고객 (JPQL: SELECT c FROM Customer c) ===");
        // Customer: 클래스명, c: 별칭(alias), c.id: Java 필드명
        List<Customer> all = em
            .createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class)
            .getResultList();
        all.forEach(c -> System.out.println("  " + c));

        // ── 2. city 조건 조회 ──────────────────────────────────────────────
        System.out.println("\n=== 2. 도시 조건 조회 (:city 파라미터 바인딩) ===");
        List<Customer> seoulCustomers = em
            .createQuery(
                "SELECT c FROM Customer c WHERE c.city = :city ORDER BY c.id",
                Customer.class)
            .setParameter("city", "서울")
            .getResultList();
        System.out.println("  서울 고객:");
        seoulCustomers.forEach(c -> System.out.println("    " + c));

        // ── 3. name LIKE 조회 ──────────────────────────────────────────────
        System.out.println("\n=== 3. 이름 LIKE 조회 ===");
        List<Customer> likeResult = em
            .createQuery(
                "SELECT c FROM Customer c WHERE c.name LIKE :pattern ORDER BY c.id",
                Customer.class)
            .setParameter("pattern", "홍%")
            .getResultList();
        System.out.println("  이름이 '홍'으로 시작하는 고객:");
        likeResult.forEach(c -> System.out.println("    " + c));

        // ── 4. 전체 Product 조회 ───────────────────────────────────────────
        System.out.println("\n=== 4. 전체 제품 (JPQL: SELECT p FROM Product p) ===");
        List<Product> products = em
            .createQuery("SELECT p FROM Product p ORDER BY p.price DESC", Product.class)
            .getResultList();
        products.forEach(p -> System.out.println("  " + p));

        // ── 5. dtype 조건 조회 ─────────────────────────────────────────────
        System.out.println("\n=== 5. dtype 조건 조회 ===");
        List<Product> physicals = em
            .createQuery(
                "SELECT p FROM Product p WHERE p.dtype = :dtype ORDER BY p.name",
                Product.class)
            .setParameter("dtype", "PHYSICAL")
            .getResultList();
        System.out.println("  PHYSICAL 제품:");
        physicals.forEach(p -> System.out.println("    " + p));

        // ── 6. 집계 함수 ───────────────────────────────────────────────────
        System.out.println("\n=== 6. 집계 함수 (COUNT, AVG) ===");
        Long customerCount = em
            .createQuery("SELECT COUNT(c) FROM Customer c", Long.class)
            .getSingleResult();
        System.out.println("  전체 고객 수: " + customerCount);

        Double avgPrice = em
            .createQuery("SELECT AVG(p.price) FROM Product p", Double.class)
            .getSingleResult();
        System.out.printf("  제품 평균 가격: %,.0f원%n", avgPrice);

        System.out.println("\n[정리]");
        System.out.println("  JPQL 기본 구문: SELECT 별칭 FROM 엔티티클래스명 별칭 [WHERE ...] [ORDER BY ...]");
        System.out.println("  :파라미터명 바인딩 → setParameter(\"파라미터명\", 값)");
        System.out.println("  createQuery(jpql, 반환타입.class) → TypedQuery (컴파일 타입 안전)");
        System.out.println("  getResultList() → List<T> / getSingleResult() → T (결과 1건)");
      }
    }
  }
}
