package com.eomcs.advanced.jpa.exam09;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam09 - 연관관계 매핑: 단방향 @ManyToOne
//
// 단방향(Unidirectional) @ManyToOne:
// - Order → Customer 방향으로만 참조한다.
// - Customer에서 Order 목록으로의 참조는 없다.
// - FK는 Order 테이블(shop_orders.customer_id)에 있다.
//
// Order.customer = new Customer() 처럼 Java 객체로 연관관계를 설정하면
// Hibernate가 FK(customer_id) 컬럼에 해당 Customer의 id 값을 넣어준다.
//
// 조인 쿼리:
// - JPQL에서 "o.customer.name"처럼 연관 엔티티 필드에 바로 접근할 수 있다.
// - Hibernate가 JOIN SQL을 자동으로 생성한다.
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam09.App
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
        Persistence.createEntityManagerFactory("exam09", props)) {

      // ── 1. 주문 조회 & 연관 고객 접근 ────────────────────────────────────
      System.out.println("=== [단방향 @ManyToOne] Order → Customer 탐색 ===");

      try (EntityManager em = emf.createEntityManager()) {
        // 주문(Order) 전체 조회
        List<Order> orders = em
            .createQuery("SELECT o FROM Order o ORDER BY o.id", Order.class)
            .getResultList();

        System.out.println("주문 목록:");
        for (Order o : orders) {
          // LAZY 로딩: o.getCustomer()를 처음 호출하는 시점에 SELECT 실행
          Customer c = o.getCustomer();
          System.out.printf("  %s → 고객: %s%n", o, c);
        }
      }

      // ── 2. JPQL JOIN으로 한 번에 조회 ───────────────────────────────────
      System.out.println("\n=== [JPQL JOIN] 주문과 고객을 함께 조회 ===");

      try (EntityManager em = emf.createEntityManager()) {
        // o.customer.name: 연관 엔티티 필드에 . 으로 접근 (SQL JOIN 자동 생성)
        List<Order> orders = em
            .createQuery(
                "SELECT o FROM Order o JOIN o.customer c ORDER BY o.id",
                Order.class)
            .getResultList();

        System.out.println("JOIN 조회 결과:");
        orders.forEach(o ->
            System.out.printf("  %s / 고객: %s%n", o, o.getCustomer()));
      }

      // ── 3. 특정 고객의 주문 조회 ─────────────────────────────────────────
      System.out.println("\n=== [WHERE 조건] 특정 고객의 주문 조회 ===");

      try (EntityManager em = emf.createEntityManager()) {
        Long customerId = 1L; // 홍길동 (sample-oracle.sql 기준)
        List<Order> customerOrders = em
            .createQuery(
                "SELECT o FROM Order o WHERE o.customer.id = :customerId ORDER BY o.id",
                Order.class)
            .setParameter("customerId", customerId)
            .getResultList();

        System.out.printf("  customer_id=%d 의 주문:%n", customerId);
        customerOrders.forEach(o ->
            System.out.printf("    %s → 고객: %s%n", o, o.getCustomer()));
      }

      // ── 4. 고객명으로 주문 조회 ───────────────────────────────────────────
      System.out.println("\n=== [연관 엔티티 조건] 고객 이름으로 주문 조회 ===");

      try (EntityManager em = emf.createEntityManager()) {
        List<Order> result = em
            .createQuery(
                "SELECT o FROM Order o WHERE o.customer.name = :name ORDER BY o.id",
                Order.class)
            .setParameter("name", "홍길동")
            .getResultList();

        System.out.println("  '홍길동' 고객의 주문:");
        result.forEach(o ->
            System.out.printf("    %s%n", o));
      }

      System.out.println("\n[정리]");
      System.out.println("  @ManyToOne: 다(N)에서 일(1)로의 단방향 참조");
      System.out.println("  @JoinColumn(name='customer_id'): FK 컬럼 지정");
      System.out.println("  LAZY 로딩: getCustomer() 최초 호출 시 SELECT 실행");
      System.out.println("  JPQL JOIN: 'SELECT o FROM Order o JOIN o.customer c'");
      System.out.println("  연관 필드 조건: 'WHERE o.customer.name = :name' (SQL JOIN 자동 생성)");
    }
  }
}
