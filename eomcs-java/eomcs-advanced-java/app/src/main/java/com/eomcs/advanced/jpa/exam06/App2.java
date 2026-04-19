package com.eomcs.advanced.jpa.exam06;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// exam06 - EntityTransaction으로 CRUD 처리
//
// EntityTransaction:
// - JPA에서 트랜잭션을 수동으로 관리하는 인터페이스다.
// - em.getTransaction()으로 획득한다.
// - begin() → 작업 → commit() 순서로 사용하고, 예외 발생 시 rollback()을 호출한다.
//
// JDBC의 connection.commit() / connection.rollback()에 대응한다.
//
// 엔티티 상태 변화:
//   persist()  : 비영속(new) → 영속(managed) → commit 시 INSERT
//   find()     : DB → 영속(managed)
//   merge()    : 준영속/비영속 → 영속(managed) → commit 시 UPDATE
//   remove()   : 영속(managed) → 삭제 예정 → commit 시 DELETE
//
// 실행 방법:
//   ./gradlew -q run -PmainClass=com.eomcs.advanced.jpa.exam06.App2
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
        Persistence.createEntityManagerFactory("exam06", props)) {

      // ── 1. INSERT ────────────────────────────────────────────────────────
      System.out.println("=== 1. INSERT (persist + commit) ===");
      Long savedId;
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();                              // 트랜잭션 시작
        try {
          Customer c = new Customer();
          c.setName("테스트고객");
          c.setEmail("test_" + System.currentTimeMillis() + "@example.com");
          c.setCity("인천");
          c.setCreatedAt(LocalDateTime.now());
          c.setUpdatedAt(LocalDateTime.now());

          em.persist(c);                         // 영속성 컨텍스트에 등록 (아직 INSERT 안 됨)
          System.out.println("  persist() 직후 id: " + c.getId());
          // IDENTITY 전략: persist() 시점에 즉시 INSERT → id 채번

          tx.commit();                           // 커밋 → (IDENTITY 이외 전략은 여기서 INSERT)
          savedId = c.getId();
          System.out.println("  commit() 후 id: " + savedId);
        } catch (Exception e) {
          tx.rollback();
          throw e;
        }
      }

      // ── 2. SELECT ────────────────────────────────────────────────────────
      System.out.println("\n=== 2. SELECT (find) ===");
      try (EntityManager em = emf.createEntityManager()) {
        // 새 EntityManager = 새 영속성 컨텍스트 → DB에서 조회
        Customer found = em.find(Customer.class, savedId);
        System.out.println("  조회 결과: " + found);
        System.out.println("  영속 상태? " + em.contains(found));
      }

      // ── 3. UPDATE ────────────────────────────────────────────────────────
      System.out.println("\n=== 3. UPDATE (변경 감지 Dirty Checking) ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
          // find() 로 조회한 엔티티는 영속(managed) 상태다.
          // 영속 상태 엔티티의 필드를 변경하면 commit() 시 자동으로 UPDATE된다.
          // → 별도의 update() 호출 없음! 이것이 "변경 감지(Dirty Checking)"다.
          Customer managed = em.find(Customer.class, savedId);
          managed.setCity("수원");              // 필드만 변경하면 됨
          System.out.println("  setCity('수원') 호출 (UPDATE SQL은 commit 시 실행)");
          tx.commit();                          // 변경 감지 → UPDATE SQL 자동 실행
          System.out.println("  commit() 완료");
        } catch (Exception e) {
          tx.rollback();
          throw e;
        }
      }

      // ── 4. DELETE ────────────────────────────────────────────────────────
      System.out.println("\n=== 4. DELETE (remove + commit) ===");
      try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
          Customer toDelete = em.find(Customer.class, savedId);
          em.remove(toDelete);                  // 삭제 예약 (아직 DELETE 안 됨)
          System.out.println("  remove() 호출 (DELETE SQL은 commit 시 실행)");
          tx.commit();                          // DELETE SQL 실행
          System.out.println("  commit() 완료 → 삭제 id=" + savedId);
        } catch (Exception e) {
          tx.rollback();
          throw e;
        }
      }

      // ── 5. 삭제 확인 ─────────────────────────────────────────────────────
      System.out.println("\n=== 5. 삭제 확인 ===");
      try (EntityManager em = emf.createEntityManager()) {
        Customer gone = em.find(Customer.class, savedId);
        System.out.println("  삭제 후 find() 결과: " + gone);  // null
      }

      System.out.println("\n[정리]");
      System.out.println("  begin() → persist/find/remove/필드수정 → commit()");
      System.out.println("  예외 발생 시 rollback() 필수 → try-catch 구조 권장");
      System.out.println("  변경 감지: find()로 조회한 엔티티 필드 수정 → commit() 시 자동 UPDATE");
    }
  }
}
