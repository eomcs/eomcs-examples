package com.eomcs.advanced.jpa.exam07;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// exam07 - 기본 엔티티 매핑: persist & find
//
// 이 예제에서 확인할 내용:
//   1. @Entity, @Table, @Id, @GeneratedValue, @Column 으로 엔티티를 정의한다.
//   2. persist() 로 새 엔티티를 저장한다 (INSERT).
//   3. find() 로 기본 키로 단건 조회한다 (SELECT).
//   4. JPQL 로 조건 조회한다 (WHERE, ORDER BY).
//   5. 변경 감지로 수정한다 (UPDATE 없이 필드만 변경).
//   6. remove() 로 삭제한다 (DELETE).
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam07.App
//
public class App {

  static EntityManagerFactory buildEmf() {
    String host    = System.getenv("DB_HOSTNAME");
    String port    = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");
    String url     = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;

    Map<String, String> props = new HashMap<>();
    props.put("jakarta.persistence.jdbc.url",      url);
    props.put("jakarta.persistence.jdbc.user",     System.getenv("DB_USERNAME"));
    props.put("jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));
    return Persistence.createEntityManagerFactory("exam07", props);
  }

  public static void main(String[] args) {

    try (EntityManagerFactory emf = buildEmf()) {

      // ── 1. 고객 INSERT ──────────────────────────────────────────────────
      System.out.println("=== 1. 고객 저장 (Customer INSERT) ===");
      Long customerId;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
          Customer c = new Customer();
          c.setName("JPA테스터");
          c.setEmail("jpa_" + System.currentTimeMillis() + "@test.com");
          c.setCity("서울");
          c.setStreet("테헤란로 1");
          c.setZipcode("06000");
          c.setCreatedAt(LocalDateTime.now());
          c.setUpdatedAt(LocalDateTime.now());

          em.persist(c);
          tx.commit();
          customerId = c.getId();
          System.out.println("  저장 완료: " + c);
        } catch (Exception e) {
          tx.rollback();
          throw e;
        }
      }

      // ── 2. 기본 키로 조회 ───────────────────────────────────────────────
      System.out.println("\n=== 2. 기본 키로 조회 (find) ===");
      try (EntityManager em = emf.createEntityManager()) {
        Customer found = em.find(Customer.class, customerId);
        System.out.println("  조회: " + found);
      }

      // ── 3. 제품 INSERT ──────────────────────────────────────────────────
      System.out.println("\n=== 3. 제품 저장 (Product INSERT) ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
          Product p = new Product();
          p.setDtype("PHYSICAL");
          p.setName("테스트 노트북");
          p.setPrice(new BigDecimal("1500000.00"));
          p.setStock(3);
          p.setCreatedAt(LocalDateTime.now());
          p.setUpdatedAt(LocalDateTime.now());

          em.persist(p);
          tx.commit();
          System.out.println("  저장 완료: " + p);
        } catch (Exception e) {
          tx.rollback();
          throw e;
        }
      }

      // ── 4. JPQL 조건 조회 ───────────────────────────────────────────────
      System.out.println("\n=== 4. JPQL 조건 조회 (WHERE, ORDER BY) ===");
      try (EntityManager em = emf.createEntityManager()) {
        // JPQL: 테이블명·컬럼명이 아닌 엔티티 클래스명·필드명을 사용한다.
        // Product.price (BigDecimal) >= 100000
        List<Product> expensive = em
            .createQuery(
                "SELECT p FROM Product p WHERE p.price >= :minPrice ORDER BY p.price DESC",
                Product.class)
            .setParameter("minPrice", new BigDecimal("100000"))
            .getResultList();
        System.out.println("  100,000원 이상 제품:");
        expensive.forEach(p -> System.out.println("    " + p));
      }

      // ── 5. 변경 감지로 UPDATE ───────────────────────────────────────────
      System.out.println("\n=== 5. 변경 감지 (Dirty Checking → UPDATE) ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
          Customer managed = em.find(Customer.class, customerId);
          System.out.println("  수정 전: " + managed.getCity());
          managed.setCity("부산");              // UPDATE SQL 호출 없이 필드만 변경
          managed.setUpdatedAt(LocalDateTime.now());
          tx.commit();                          // commit 시 Hibernate가 변경 감지 → UPDATE
          System.out.println("  수정 후: " + managed.getCity());
        } catch (Exception e) {
          tx.rollback();
          throw e;
        }
      }

      // ── 6. 삭제 후 확인 ─────────────────────────────────────────────────
      System.out.println("\n=== 6. 삭제 (remove) ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
          Customer toDelete = em.find(Customer.class, customerId);
          em.remove(toDelete);
          tx.commit();
          System.out.println("  삭제 완료 id=" + customerId);
        } catch (Exception e) {
          tx.rollback();
          throw e;
        }
      }

      try (EntityManager em = emf.createEntityManager()) {
        Customer gone = em.find(Customer.class, customerId);
        System.out.println("  삭제 후 find() : " + gone); // null
      }

      System.out.println("\n[정리]");
      System.out.println("  @Entity + @Table + @Column → 클래스-테이블 매핑");
      System.out.println("  @Id + @GeneratedValue(IDENTITY) → Oracle IDENTITY 컬럼 대응");
      System.out.println("  persist() → INSERT / find() → SELECT / remove() → DELETE");
      System.out.println("  변경 감지: 영속 엔티티 필드 수정 → commit() 시 자동 UPDATE");
    }
  }
}
