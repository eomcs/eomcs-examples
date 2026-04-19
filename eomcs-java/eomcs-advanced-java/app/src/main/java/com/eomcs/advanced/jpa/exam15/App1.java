package com.eomcs.advanced.jpa.exam15;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam15 - 복합 키 매핑: @IdClass 방식
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam15.App1
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
        Persistence.createEntityManagerFactory("exam15", props)) {

      // ── 1. 기존 샘플 데이터 조회 ──────────────────────────────────────────
      System.out.println("=== 1. 전체 주문 상세 조회 (@IdClass) ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<OrderItem> items = em.createQuery(
            "SELECT oi FROM OrderItem oi ORDER BY oi.orderId, oi.productId",
            OrderItem.class)
            .getResultList();
        // JPQL에서 복합 PK 필드를 엔티티 필드처럼 직접 사용 가능 (@IdClass 장점)
        items.forEach(oi -> System.out.println("  " + oi));
      }

      // ── 2. 복합 PK로 단건 조회 ────────────────────────────────────────────
      System.out.println("\n=== 2. 복합 PK(OrderItemId)로 단건 조회 ===");
      try (EntityManager em = emf.createEntityManager()) {
        OrderItemId pk = new OrderItemId(1L, 1L); // order_id=1, product_id=1
        OrderItem found = em.find(OrderItem.class, pk);
        System.out.println("  find(OrderItemId{1,1}): " + found);
      }

      // ── 3. 새 OrderItem INSERT ────────────────────────────────────────────
      System.out.println("\n=== 3. OrderItem INSERT ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        OrderItem newItem = new OrderItem();
        newItem.setOrderId(3L);      // 기존 주문 3번
        newItem.setProductId(1L);    // 기존 제품 1번 (MacBook Pro)
        newItem.setQuantity(1);
        newItem.setPrice(new BigDecimal("2990000"));

        em.persist(newItem);
        tx.commit();
        System.out.println("  저장 완료: " + newItem);
      }

      // ── 4. JPQL: 특정 주문의 상세 조회 ───────────────────────────────────
      System.out.println("\n=== 4. JPQL: orderId = 1 인 주문 상세 ===");
      try (EntityManager em = emf.createEntityManager()) {
        List<OrderItem> order1Items = em.createQuery(
            "SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId ORDER BY oi.productId",
            OrderItem.class)
            .setParameter("orderId", 1L)
            .getResultList();
        // @IdClass 방식: oi.orderId 처럼 엔티티 필드 직접 접근
        order1Items.forEach(oi -> System.out.println("  " + oi));
      }

      // ── 5. UPDATE (변경 감지) ─────────────────────────────────────────────
      System.out.println("\n=== 5. 수량 변경 (Dirty Checking) ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        OrderItemId pk = new OrderItemId(3L, 1L);
        OrderItem managed = em.find(OrderItem.class, pk);
        System.out.println("  변경 전: " + managed);
        managed.setQuantity(2);

        tx.commit();
        System.out.println("  변경 후 commit → UPDATE 실행");
      }

      // ── 6. DELETE ─────────────────────────────────────────────────────────
      System.out.println("\n=== 6. OrderItem 삭제 ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        OrderItemId pk = new OrderItemId(3L, 1L);
        OrderItem toRemove = em.find(OrderItem.class, pk);
        if (toRemove != null) {
          em.remove(toRemove);
          System.out.println("  삭제 예약: " + toRemove);
        }

        tx.commit();
        System.out.println("  DELETE 실행 완료");
      }

      System.out.println("\n[정리: @IdClass 방식]");
      System.out.println("  - 엔티티에 @Id를 여러 개 선언");
      System.out.println("  - JPQL: oi.orderId 처럼 필드 직접 접근 (단순)");
      System.out.println("  - find() 인수: new OrderItemId(orderId, productId)");
    }
  }
}
