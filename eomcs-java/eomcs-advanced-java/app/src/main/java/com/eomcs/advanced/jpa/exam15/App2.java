package com.eomcs.advanced.jpa.exam15;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam15 - 복합 키 매핑: @EmbeddedId 방식
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam15.App2
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
        Persistence.createEntityManagerFactory("exam15", props)) {

      // ── 1. 기존 샘플 데이터 조회 ──────────────────────────────────────────
      System.out.println("=== 1. 전체 주문 상세 조회 (@EmbeddedId) ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<OrderItemV2> items = em.createQuery(
            "SELECT oi FROM OrderItemV2 oi ORDER BY oi.id.orderId, oi.id.productId",
            OrderItemV2.class)
            .getResultList();
        // JPQL에서 복합 PK 접근: oi.id.orderId (@EmbeddedId 방식)
        items.forEach(oi -> System.out.println("  " + oi));
      }

      // ── 2. 복합 PK로 단건 조회 ────────────────────────────────────────────
      System.out.println("\n=== 2. 복합 PK(OrderItemPK)로 단건 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        OrderItemPK pk = new OrderItemPK(1L, 1L); // order_id=1, product_id=1
        OrderItemV2 found = em.find(OrderItemV2.class, pk);
        System.out.println("  find(OrderItemPK{1,1}): " + found);
      }

      // ── 3. 새 OrderItemV2 INSERT ──────────────────────────────────────────
      System.out.println("\n=== 3. OrderItemV2 INSERT ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        OrderItemV2 newItem = new OrderItemV2();
        newItem.setId(new OrderItemPK(3L, 5L)); // order_id=3, product_id=5 (울 코트)
        newItem.setQuantity(1);
        newItem.setPrice(new BigDecimal("189000"));

        em.persist(newItem);
        tx.commit();
        System.out.println("  저장 완료: " + newItem);
      }

      // ── 4. JPQL: id.orderId 경로로 조회 ──────────────────────────────────
      System.out.println("\n=== 4. JPQL: id.orderId = 1 인 주문 상세 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<OrderItemV2> order1Items = em.createQuery(
            "SELECT oi FROM OrderItemV2 oi WHERE oi.id.orderId = :orderId ORDER BY oi.id.productId",
            OrderItemV2.class)
            .setParameter("orderId", 1L)
            .getResultList();
        // @EmbeddedId 방식: oi.id.orderId 처럼 PK 객체를 거쳐 접근
        order1Items.forEach(oi -> System.out.println("  " + oi));
      }

      // ── 5. DELETE ─────────────────────────────────────────────────────────
      System.out.println("\n=== 5. OrderItemV2 삭제 ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        OrderItemPK pk = new OrderItemPK(3L, 5L);
        OrderItemV2 toRemove = em.find(OrderItemV2.class, pk);
        if (toRemove != null) {
          em.remove(toRemove);
        }

        tx.commit();
        System.out.println("  DELETE 실행 완료");
      }

      System.out.println("\n[정리: @EmbeddedId 방식]");
      System.out.println("  - @EmbeddedId로 PK 객체를 단일 필드로 선언");
      System.out.println("  - JPQL: oi.id.orderId 처럼 PK 객체를 거쳐 접근");
      System.out.println("  - find() 인수: new OrderItemPK(orderId, productId)");
      System.out.println();
      System.out.println("  @IdClass vs @EmbeddedId");
      System.out.println("  @IdClass  : PK 필드가 엔티티에 직접 노출 → JPQL 단순");
      System.out.println("  @EmbeddedId: PK 객체로 묶임 → PK 자체를 다루기 편리");
    }
  }
}
