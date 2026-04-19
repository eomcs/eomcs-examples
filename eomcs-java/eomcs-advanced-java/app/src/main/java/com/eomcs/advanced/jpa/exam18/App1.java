package com.eomcs.advanced.jpa.exam18;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam18 - JPQL 심화: JOIN FETCH (N+1 문제 해결)
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam18.App1
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
        Persistence.createEntityManagerFactory("exam18", props)) {

      // ── 1. N+1 문제 발생 (일반 JPQL + 지연 로딩) ────────────────────────────
      System.out.println("=== 1. N+1 문제: 주문 목록 조회 후 고객 지연 로딩 ===");
      System.out.println("  (콘솔의 SQL 로그를 확인하면 주문 수만큼 추가 SELECT 발생)");
      try (EntityManager em = emf.createEntityManager()) {
        // 1번 SELECT: 주문 3건 조회
        List<Order> orders = em.createQuery(
            "SELECT o FROM Order o ORDER BY o.id",
            Order.class)
            .getResultList();

        System.out.printf("  주문 %d건 조회 완료. 고객 이름 접근 시 추가 SELECT 발생:%n",
            orders.size());

        // 각 주문마다 o.getCustomer() 접근 시 customer 지연 로딩 → SELECT 발생 (N+1)
        for (Order o : orders) {
          // customer 가 1차 캐시에 없으면 추가 SELECT 실행
          System.out.printf("    주문 id=%d | 고객: %s | 상태: %s%n",
              o.getId(), o.getCustomer().getName(), o.getOrderStatus());
        }
        // 주문이 N건이면 최대 N번의 추가 SELECT 발생 → N+1 문제
      }

      // ── 2. JOIN FETCH 로 해결 ──────────────────────────────────────────────
      System.out.println("\n=== 2. JOIN FETCH: 주문 + 고객을 한 번에 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        // JOIN FETCH: Order 조회 시 Customer 를 즉시 함께 로드
        // → SELECT o.*, c.* FROM shop_orders o INNER JOIN shop_customer c ON ...
        List<Order> orders = em.createQuery(
            "SELECT o FROM Order o JOIN FETCH o.customer ORDER BY o.id",
            Order.class)
            .getResultList();

        System.out.println("  JOIN FETCH 후 고객 접근 (추가 SELECT 없음):");
        for (Order o : orders) {
          System.out.printf("    주문 id=%d | 고객: %s | 도시: %s%n",
              o.getId(), o.getCustomer().getName(), o.getCustomer().getCity());
        }
        // JOIN FETCH 이후 getCustomer()는 이미 로드된 데이터를 반환 (추가 쿼리 없음)
      }

      // ── 3. 컬렉션 JOIN FETCH (OneToMany) ─────────────────────────────────────
      System.out.println("\n=== 3. 컬렉션 JOIN FETCH: 주문 + 주문 상품 목록 ===");
      try (EntityManager em = emf.createEntityManager()) {
        // DISTINCT: OneToMany JOIN FETCH 시 Order 가 orderItems 수만큼 중복될 수 있음
        List<Order> orders = em.createQuery(
            "SELECT DISTINCT o FROM Order o JOIN FETCH o.orderItems ORDER BY o.id",
            Order.class)
            .getResultList();

        for (Order o : orders) {
          System.out.printf("  주문 id=%d [%s]:%n", o.getId(), o.getOrderStatus());
          o.getOrderItems().forEach(item ->
              System.out.printf("    - 제품id=%d, 수량=%d, 단가=%s%n",
                  item.getProductId(), item.getQuantity(), item.getPrice()));
        }
        // DISTINCT 없으면 JOIN 결과만큼 Order 중복 반환
        // Hibernate 6: DISTINCT HQL이 SQL DISTINCT와 in-memory dedup 모두 수행
      }

      // ── 4. 다중 JOIN FETCH (고객 + 주문 상품 동시 로드) ─────────────────────
      System.out.println("\n=== 4. 다중 JOIN FETCH: 주문 + 고객 + 주문상품 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<Order> orders = em.createQuery(
            "SELECT DISTINCT o FROM Order o"
            + " JOIN FETCH o.customer"
            + " JOIN FETCH o.orderItems"
            + " ORDER BY o.id",
            Order.class)
            .getResultList();

        for (Order o : orders) {
          System.out.printf("  주문 id=%d | 고객: %-6s | 상품 %d종%n",
              o.getId(),
              o.getCustomer().getName(),
              o.getOrderItems().size());
        }
        // 주의: 컬렉션 JOIN FETCH 는 2개 이상 동시 사용 시 MultipleBagFetchException 발생 가능
        //       → List 대신 Set 사용하거나 한 번에 하나씩 JOIN FETCH 권장
      }

      System.out.println("\n[정리: JOIN FETCH]");
      System.out.println("  N+1 문제   : 1번 쿼리로 N건 조회 → 각 연관 접근 시 N번 추가 SELECT");
      System.out.println("  JOIN FETCH : 연관 엔티티를 즉시 함께 로드 → 쿼리 1번으로 해결");
      System.out.println("  컬렉션     : DISTINCT 로 중복 제거 필요 (OneToMany JOIN FETCH 시)");
      System.out.println("  다중       : 컬렉션 JOIN FETCH 2개 이상 → MultipleBagFetchException 주의");
    }
  }
}
