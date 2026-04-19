package com.eomcs.advanced.jpa.exam18;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam18 - JPQL 심화: 서브쿼리
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam18.App2
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
        Persistence.createEntityManagerFactory("exam18", props)) {

      // ── 1. EXISTS 서브쿼리: 주문이 있는 고객 ────────────────────────────────
      System.out.println("=== 1. EXISTS: 주문이 있는 고객 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> result = em.createQuery(
            "SELECT c FROM Customer c"
            + " WHERE EXISTS (SELECT o FROM Order o WHERE o.customer = c)"
            + " ORDER BY c.id",
            Customer.class)
            .getResultList();

        result.forEach(c -> System.out.println("  " + c));
        // EXISTS: 서브쿼리에 결과가 하나라도 있으면 true
      }

      // ── 2. NOT EXISTS: 주문이 없는 고객 ─────────────────────────────────────
      System.out.println("\n=== 2. NOT EXISTS: 주문이 없는 고객 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> result = em.createQuery(
            "SELECT c FROM Customer c"
            + " WHERE NOT EXISTS (SELECT o FROM Order o WHERE o.customer = c)"
            + " ORDER BY c.id",
            Customer.class)
            .getResultList();

        if (result.isEmpty()) {
          System.out.println("  (모든 고객이 주문을 보유하고 있음)");
        } else {
          result.forEach(c -> System.out.println("  " + c));
        }
      }

      // ── 3. IN 서브쿼리: 주문한 고객 ID 목록으로 필터 ─────────────────────────
      System.out.println("\n=== 3. IN + 서브쿼리: 주문한 고객 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Customer> result = em.createQuery(
            "SELECT c FROM Customer c"
            + " WHERE c.id IN (SELECT o.customer.id FROM Order o)"
            + " ORDER BY c.id",
            Customer.class)
            .getResultList();

        result.forEach(c -> System.out.println("  " + c));
        // IN + 서브쿼리: EXISTS 보다 직관적이지만 중복 제거가 자동으로 되지 않을 수 있음
      }

      // ── 4. 스칼라 서브쿼리: 평균 가격보다 비싼 제품 ─────────────────────────
      System.out.println("\n=== 4. 스칼라 서브쿼리: 평균 가격 초과 제품 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Product> result = em.createQuery(
            "SELECT p FROM Product p"
            + " WHERE p.price > (SELECT AVG(p2.price) FROM Product p2)"
            + " ORDER BY p.price DESC",
            Product.class)
            .getResultList();

        System.out.println("  평균 가격 초과 제품:");
        result.forEach(p -> System.out.printf("  %-30s %s원%n", p.getName(), p.getPrice()));
      }

      // ── 5. ALL 서브쿼리: 모든 제품보다 비싼 주문 상품 ──────────────────────
      System.out.println("\n=== 5. ALL 서브쿼리: 최고가 제품 이상 가격의 주문 상품 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<OrderItem> result = em.createQuery(
            "SELECT oi FROM OrderItem oi"
            + " WHERE oi.price >= ALL (SELECT p.price FROM Product p)"
            + " ORDER BY oi.price DESC",
            OrderItem.class)
            .getResultList();

        if (result.isEmpty()) {
          System.out.println("  (해당 조건의 주문 상품 없음)");
        } else {
          result.forEach(oi -> System.out.println("  " + oi));
        }
        // ALL: 서브쿼리의 모든 결과와 비교 조건이 참이면 true
      }

      // ── 6. 서브쿼리 + JOIN: CONFIRMED 주문 고객의 제품 조회 ────────────────
      System.out.println("\n=== 6. 복합 서브쿼리: CONFIRMED 주문 고객이 주문한 제품 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Product> result = em.createQuery(
            "SELECT p FROM Product p"
            + " WHERE p.id IN ("
            + "   SELECT oi.productId FROM OrderItem oi"
            + "   WHERE oi.orderId IN ("
            + "     SELECT o.id FROM Order o WHERE o.orderStatus = 'CONFIRMED'"
            + "   )"
            + " )"
            + " ORDER BY p.id",
            Product.class)
            .getResultList();

        System.out.println("  CONFIRMED 주문에 포함된 제품:");
        result.forEach(p -> System.out.println("  " + p));
      }

      System.out.println("\n[정리: JPQL 서브쿼리]");
      System.out.println("  EXISTS     : 서브쿼리에 결과가 하나라도 존재하면 true");
      System.out.println("  NOT EXISTS : 서브쿼리에 결과가 없으면 true");
      System.out.println("  IN         : 서브쿼리 결과 집합에 포함되면 true");
      System.out.println("  ALL        : 서브쿼리 모든 값과 비교 조건이 모두 true");
      System.out.println("  스칼라     : WHERE 절에서 단일 값 반환 서브쿼리 사용 가능");
      System.out.println("  주의       : JPQL 서브쿼리는 WHERE/HAVING 절에서만 사용 가능");
      System.out.println("               (SELECT/FROM 절 서브쿼리는 Hibernate 6에서 일부 지원)");
    }
  }
}
