package com.eomcs.advanced.jpa.exam16;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

// exam16 - 엔티티 이벤트 & @EntityListeners (Auditing)
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam16.App
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
        Persistence.createEntityManagerFactory("exam16", props)) {

      // ── 1. persist() → @PrePersist, @PostPersist 호출 확인 ───────────────
      System.out.println("=== 1. INSERT: @PrePersist / @PostPersist ===");
      System.out.println("  (createdAt/updatedAt을 코드에서 직접 설정하지 않는다)");
      Long savedId;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = new Customer();
        customer.setName("이지은");
        customer.setEmail("jieun_" + System.currentTimeMillis() + "@test.com");
        customer.setCity("서울");
        // createdAt / updatedAt 을 설정하지 않아도
        // @PrePersist → AuditListener.prePersist() 에서 자동 설정된다.

        em.persist(customer);  // ← 이 시점에 @PrePersist 호출
        tx.commit();           // ← commit 후 @PostPersist 호출

        savedId = customer.getId();
        System.out.println("  저장 완료 - createdAt: " + customer.getCreatedAt());
        System.out.println("  저장 완료 - updatedAt: " + customer.getUpdatedAt());
      }

      // ── 2. find() → @PostLoad 호출 확인 ──────────────────────────────────
      System.out.println("\n=== 2. SELECT: @PostLoad ===");
      try (EntityManager em = emf.createEntityManager()) {
        Customer found = em.find(Customer.class, savedId); // ← @PostLoad 호출
        System.out.println("  조회 결과: " + found);
      }

      // ── 3. 변경 감지(Dirty Checking) → @PreUpdate, @PostUpdate 호출 확인 ──
      System.out.println("\n=== 3. UPDATE: @PreUpdate / @PostUpdate ===");
      System.out.println("  (updatedAt을 코드에서 직접 설정하지 않는다)");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer managed = em.find(Customer.class, savedId);
        managed.setCity("부산");
        // updatedAt을 직접 설정하지 않아도
        // commit() 직전 @PreUpdate → AuditListener.preUpdate()에서 자동 갱신된다.

        tx.commit(); // ← @PreUpdate 호출 후 UPDATE SQL 실행, 이후 @PostUpdate 호출
      }

      // ── 4. 변경 후 updatedAt 확인 ─────────────────────────────────────────
      System.out.println("\n=== 4. 수정 후 updatedAt 확인 ===");
      try (EntityManager em = emf.createEntityManager()) {
        Customer check = em.find(Customer.class, savedId);
        System.out.println("  city: " + check.getCity());
        System.out.println("  updatedAt: " + check.getUpdatedAt());
      }

      // ── 5. remove() → @PreRemove, @PostRemove 호출 확인 ──────────────────
      System.out.println("\n=== 5. DELETE: @PreRemove / @PostRemove ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer toRemove = em.find(Customer.class, savedId);
        em.remove(toRemove); // ← @PreRemove 호출
        tx.commit();         // ← DELETE SQL 실행 후 @PostRemove 호출
      }

      System.out.println("\n[정리: @EntityListeners 이벤트 순서]");
      System.out.println("  INSERT: @PrePersist → INSERT SQL → @PostPersist");
      System.out.println("  UPDATE: @PreUpdate  → UPDATE SQL → @PostUpdate");
      System.out.println("  DELETE: @PreRemove  → DELETE SQL → @PostRemove");
      System.out.println("  SELECT: @PostLoad (조회 후)");
      System.out.println();
      System.out.println("  활용: createdAt/updatedAt 자동 관리 → Auditing");
      System.out.println("  참고: Spring Data JPA @EnableJpaAuditing은 이 원리를 추상화한 것이다.");
    }
  }
}
